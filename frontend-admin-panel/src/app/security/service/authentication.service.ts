import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from './config.service';
import {Router} from '@angular/router';
import {map} from 'rxjs/operators';

interface LoginRequestBody {
  username: string;
  password: string;
}

@Injectable()
export class AuthenticationService {


  constructor(private http: HttpClient, private configService: ConfigService, private router: Router) {
  }

  login(username: string, password: string) {
    const loginRequestBody: LoginRequestBody = {username, password};
    return this.http.post<any>(this.configService.getBaseUrl() + '/api/auth/logon', loginRequestBody, { withCredentials: true})
      .pipe(map(() => {
        // login successful if there's a jwt token in the response
        // localStorage.setItem('currentUser', 'yes');

      }));
  }

  validate() {
    return this.http.post<any>(this.configService.getBaseUrl() + '/api/auth/validate', '', { withCredentials: true});
  }

  logout() {
    // TODO: endpoint url
  }
}
