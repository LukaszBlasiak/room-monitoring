import {Injectable, OnDestroy} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {Config} from '../../common/config';
import {interval, Subscription} from 'rxjs';
import {User} from '../model';
import {ConfigService} from './config.service';
import {Router, RouterState} from '@angular/router';
import {Observable} from 'rxjs';

@Injectable()
export class AuthenticationService implements OnDestroy {

  private keepAliveSubscription: Subscription;
  private username: string;
  private password: string;

  constructor(private http: HttpClient, private configService: ConfigService, private router: Router) {
  }

  ngOnDestroy() {
    if (this.keepAliveSubscription != null) {
      this.keepAliveSubscription.unsubscribe();
    }
  }

  login(username: string, password: string) {
    let headers: HttpHeaders = new HttpHeaders();
    headers = headers.append('Authorization', 'Basic ' + btoa(username + ':' + password));
    return this.http.post<any>(this.configService.getBaseUrl() + '/api/logon', null, {headers: headers})
      .pipe(map((user: User) => {
        // login successful if there's a jwt token in the response
        localStorage.setItem('currentUser', 'yes');
        this.username = username;
        this.password = password;
        return user;
      }));
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
  }

  public startKeepAlivePooling(): void {
    // this.performSingleKeepAlive();
    // this.keepAliveSubscription = interval(10000)
    //   .subscribe((val) => {
    //     this.performSingleKeepAlive();
    //   });
  }

  private performSingleKeepAlive() {
    if (this.router.url.includes('login')) {
      return;
    }
    let headers: HttpHeaders = new HttpHeaders();
    headers = headers.append('Authorization', 'Basic ' + btoa(this.username + ':' + this.password));
    this.http.post<any>(this.configService.getBaseUrl() + '/api/logon/keepAlive', null, {withCredentials: true, headers: headers})
      .subscribe(
        (resp) => {
        },
        error => {
          const state: RouterState = this.router.routerState;
          this.router.navigate(['login'], {queryParams: {returnUrl: state.snapshot.url}});
        }
      );
  }
}
