import {Injectable, Injector} from '@angular/core';
import {HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {Router} from '@angular/router';
import {Cookie} from 'ng2-cookies/ng2-cookies';
import {AuthenticationService} from '../service/authentication.service';

@Injectable()
export class UnauthorizedInterceptor implements HttpInterceptor {

  constructor(private _router: Router, private _authenticationService: AuthenticationService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let clonedHeaders = request.headers;
    const jwtToken = this._authenticationService.getRawToken();
    if (jwtToken) {
      clonedHeaders = clonedHeaders.append('Authorization', `Bearer ${jwtToken}`);
    }
    const clonedRequest = request.clone({headers: clonedHeaders});
    return next.handle(clonedRequest).pipe(tap(() => {
      },
      (err: any) => {
        if (err instanceof HttpErrorResponse) {
          if ((err.status === 401 || err.status === 403) && !request.url.includes('login')) {
            this._router.navigate(['login']);
          }
        }
      }));
  }
}
