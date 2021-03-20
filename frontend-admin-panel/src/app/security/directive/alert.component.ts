import {Component, OnDestroy} from '@angular/core';

import {Subscription} from 'rxjs';
import {AlertService} from '../service/alert.service';
import {ErrorModel} from '../model/error.model';

@Component({
  selector: 'app-alert',
  templateUrl: 'alert.component.html'
})

export class AlertComponent implements OnDestroy {
  private subscription: Subscription;
  private _errorModel: ErrorModel;

  get errorModel(): ErrorModel {
    return this._errorModel;
  }

  constructor(private alertService: AlertService) {
    // subscribe to alert messages
    this.subscription = alertService.getMessage().subscribe((message: ErrorModel) => {
      console.log(message);
      this._errorModel = message;
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
