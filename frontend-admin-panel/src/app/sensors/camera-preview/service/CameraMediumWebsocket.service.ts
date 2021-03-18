import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import {Injectable} from '@angular/core';
import {Observable, Subject, Subscription} from 'rxjs';
import {ConfigService} from '../../../security/service/config.service';
import {ImageModel} from '../component/medium-room-preview/medium-room-preview.component';
import {AuthenticationService} from '../../../security/service/authentication.service';

@Injectable()
export class CameraMediumWebsocketService {
  webSocketEndPoint;
  previewUrl = '/user/preview/medium';
  previewInitializationUrl = '/ws/preview/medium/start';
  stompClient: any;
  connectionReattemptTimeout = 5000;
  private _failedAttempts = 0;
  private _failedAttemptsLimit = 5;

  private imageSubject = new Subject<ImageModel>();

  constructor(private _configService: ConfigService, private _authenticationService: AuthenticationService) {
    this.webSocketEndPoint = _configService.getBaseUrl() + '/ws-smart-home-register-endpoint';
  }


  _connect() {
    const jwtToken = this._authenticationService.getRawToken();
    const ws = new SockJS(this.webSocketEndPoint + `?Authorization=Bearer ${jwtToken}`);
    this.stompClient = Stomp.over(ws);
    const that = this;
    that.stompClient.connect({Authorization: 'Bearer ' + jwtToken}, (frame) => {
      that.stompClient.subscribe(that.previewUrl, (sdkEvent) => {
        that.onMessageReceived(sdkEvent);
      });
      this._initializePreview();
      // _this.stompClient.reconnect_delay = 2000;
    }, this.errorCallBack.bind(this));
  }

  _disconnect() {
    if (this.stompClient != null) {
      this.stompClient.disconnect();
      this.stompClient = null;
    }
  }

  // on error, schedule a reconnection attempt
  private errorCallBack(error) {
    if (this._failedAttempts >= this._failedAttemptsLimit) {
      return;
    }
    setTimeout(() => {
      this._failedAttempts++;
      this._connect();
    }, this.connectionReattemptTimeout);
  }

  private onMessageReceived(response) {
    this.imageSubject.next(JSON.parse(response.body));
  }

  public getImagePreviewSubscription(): Observable<ImageModel> {
    return this.imageSubject.asObservable();
}

  private _initializePreview() {
    this.stompClient.send(this.previewInitializationUrl, {}, '');
  }
}
