﻿import {Injectable} from '@angular/core';
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
    return this.http.post<any>(this.configService.getBaseUrl() + '/api/auth/logon', loginRequestBody)
      .pipe(map(() => {
        // login successful if there's a jwt token in the response
        localStorage.setItem('currentUser', username);

      }));
  }

  validate() {
    return this.http.post<any>(this.configService.getBaseUrl() + '/api/auth/validate', '');
  }

  logout() {
    localStorage.clear();
    this.http.post<any>(this.configService.getBaseUrl() + '/api/auth/logout', '').subscribe();
  }
}
