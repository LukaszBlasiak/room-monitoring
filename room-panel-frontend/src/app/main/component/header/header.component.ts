import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {AuthenticationService} from '../../../security/service/authentication.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.sass']
})
export class HeaderComponent implements OnInit, OnDestroy {

  public isAuthenticated = false;
  private _subscription: Subscription;

  constructor(private _authenticationService: AuthenticationService) {
  }

  ngOnInit() {
    this._subscription =
      this._authenticationService.isAuthenticated.subscribe(_isAuthenticated => this.isAuthenticated = _isAuthenticated);
  }

  ngOnDestroy(): void {
    this._subscription.unsubscribe();
  }

}
