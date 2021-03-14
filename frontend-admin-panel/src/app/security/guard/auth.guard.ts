import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {catchError, map} from 'rxjs/operators';
import { of } from 'rxjs';
import {AuthenticationService} from '../service';

@Injectable()
export class AuthGuard implements CanActivate {

    constructor(private router: Router, private _authService: AuthenticationService) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        // if (localStorage.getItem('currentUser')) {
        //     // logged in so return true
        //     return true;
        // }
        // // not logged in so redirect to login page with the return url
        // this.router.navigate(['login'], { queryParams: { returnUrl: state.url }});
        // return false;
      return this._authService.validate().pipe(
        map(res => true),
        catchError(val => {
           this.router.navigate(['login'], { queryParams: { returnUrl: state.url }});
           return of(false);
        })
      );
    }
}
