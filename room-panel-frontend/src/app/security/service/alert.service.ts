import {Injectable} from '@angular/core';
import {Router, NavigationStart} from '@angular/router';
import {Observable, Subject} from 'rxjs';
import {ErrorModel} from '../model/error.model';

@Injectable()
export class AlertService {
  private subject = new Subject<ErrorModel>();
  private keepAfterNavigationChange = false;

  constructor(private router: Router) {
    // clear alert message on route change
    router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        if (this.keepAfterNavigationChange) {
          // only keep for a single location change
          this.keepAfterNavigationChange = false;
        } else {
          // clear alert
          this.subject.next();
        }
      }
    });
  }

  success(message: string, keepAfterNavigationChange = false): void {
    this.keepAfterNavigationChange = keepAfterNavigationChange;
    this.subject.next({ type: 'success', message });
  }

  error(message: string, keepAfterNavigationChange = false): void {
    this.keepAfterNavigationChange = keepAfterNavigationChange;
    this.subject.next({type: 'error', message});
  }

  getMessage(): Observable<ErrorModel> {
    return this.subject.asObservable();
  }
}
