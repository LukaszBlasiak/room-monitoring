import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from './config.service';
import {map} from 'rxjs/operators';
import {LoginRequestBody} from '../model/login-request.model';
import {LoginResponseModel} from '../model/login-response.model';
import {JwtHelperService} from '@auth0/angular-jwt';
import {BehaviorSubject, Observable} from 'rxjs';
import {Router} from '@angular/router';


@Injectable()
export class AuthenticationService {

  private _jwtModel: LoginResponseModel;
  private _jwtHelper = new JwtHelperService();
  private _isAuthenticated = new BehaviorSubject(false);

  constructor(private _router: Router, private _http: HttpClient, private _configService: ConfigService) {
  }

  get isAuthenticated(): Observable<boolean> {
    return this._isAuthenticated.asObservable();
  }

  login(username: string, password: string) {
    const loginRequestBody: LoginRequestBody = {username, password};
    return this._http.post<any>(this._configService.getBaseUrl() + '/api/auth/logon', loginRequestBody)
      .pipe(map((response: LoginResponseModel) => {
        // localStorage.setItem('currentUser', username);
        this._jwtModel = response;
        this._isAuthenticated.next(true);
      }));
  }

  validate(): boolean {
    return this._jwtModel
      && this._jwtModel.token
      && !this._jwtHelper.isTokenExpired(this._jwtModel.token);
  }

  public getRawToken(): string {
    if (this._jwtModel) {
      return this._jwtModel.token;
    } else {
      return null;
    }
  }

  public logout(): void {
    this._jwtModel = null;
    this._isAuthenticated.next(false);
    this._router.navigate(['login']);
  }

}
