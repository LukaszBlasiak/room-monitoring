import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {Config} from '../../common/config';
import {User} from '../model';

@Injectable()
export class AuthenticationService {
  constructor(private http: HttpClient) {
  }

  login(username: string, password: string) {
    let headers: HttpHeaders = new HttpHeaders();
    headers = headers.append('Authorization', 'Basic ' + btoa(username + ':' + password));
    return this.http.post<any>(Config.API_BASE_URL + '/logon', null, {headers: headers})
      .pipe(map((user: User) => {
        // login successful if there's a jwt token in the response
        localStorage.setItem('currentUser', 'yes');

        return user;
      }));
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
  }
}
