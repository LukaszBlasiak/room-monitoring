import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from './config.service';
import {map} from 'rxjs/operators';
import {LoginRequestBody} from '../model/login-request.model';
import {LoginResponseModel} from '../model/login-response.model';
import {JwtHelperService} from '@auth0/angular-jwt';


@Injectable()
export class AuthenticationService {

  private _jwtModel: LoginResponseModel;
  private _jwtHelper = new JwtHelperService();

  constructor(private _http: HttpClient, private _configService: ConfigService) {
  }

  login(username: string, password: string) {
    const loginRequestBody: LoginRequestBody = {username, password};
    return this._http.post<any>(this._configService.getBaseUrl() + '/api/auth/logon', loginRequestBody)
      .pipe(map((response: LoginResponseModel) => {
        // login successful if there's a jwt token in the response
        localStorage.setItem('currentUser', username);
        this._jwtModel = response;
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

}
