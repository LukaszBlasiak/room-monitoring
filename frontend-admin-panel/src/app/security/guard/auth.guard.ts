import {Injectable} from '@angular/core';
import {Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import {AuthenticationService} from '../service/authentication.service';

@Injectable()
export class AuthGuard implements CanActivate {

  constructor(private router: Router, private _authService: AuthenticationService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const isValid = this._authService.validate();
    if (isValid) {
      return true;
    } else {
      this.router.navigate(['login'], {queryParams: {returnUrl: state.url}});
      return false;
    }
  }
}
