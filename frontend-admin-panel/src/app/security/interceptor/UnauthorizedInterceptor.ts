import {Injectable} from '@angular/core';
import {HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {Router} from '@angular/router';
import {AuthenticationService} from '../service/authentication.service';

@Injectable()
export class UnauthorizedInterceptor implements HttpInterceptor {

  private _authorizationHeaderName = 'Authorization';
  private _authorizationHeaderValuePrefix = 'Bearer';

  constructor(private _router: Router, private _authenticationService: AuthenticationService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let clonedHeaders = request.headers;
    const jwtToken = this._authenticationService.getRawToken();
    if (jwtToken) {
      clonedHeaders = clonedHeaders.append(this._authorizationHeaderName, `${this._authorizationHeaderValuePrefix} ${jwtToken}`);
    }
    const clonedRequest = request.clone({headers: clonedHeaders});
    return next.handle(clonedRequest).pipe(tap(() => {
      },
      (err: HttpErrorResponse | any) => {
        if (this.isAuthenticationError(err, request)) {
          this._router.navigate(['login']);
        }
      }));
  }

  private isAuthenticationError(error: HttpErrorResponse | any, request: HttpRequest<any>): boolean {
    return error instanceof HttpErrorResponse && (error.status === 401 || error.status === 403) && !request.url.includes('login');
  }
}
