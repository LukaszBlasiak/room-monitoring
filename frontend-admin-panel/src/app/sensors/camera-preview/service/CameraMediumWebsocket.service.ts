import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {ConfigService} from '../../../security/service/config.service';
import {ImageModel} from '../component/medium-room-preview/medium-room-preview.component';
import {AuthenticationService} from '../../../security/service/authentication.service';

@Injectable()
export class CameraMediumWebsocketService {
  private readonly _webSocketEndPoint;
  private _previewUrl = '/user/preview/medium';
  private _previewInitializationUrl = '/ws/preview/medium/start';
  private _stompClient: any;
  private _connectionReattemptTimeout = 5000;
  private _failedAttempts = 0;
  private _failedAttemptsLimit = 5;

  private imageSubject = new Subject<ImageModel>();

  constructor(private _configService: ConfigService, private _authenticationService: AuthenticationService) {
    this._webSocketEndPoint = _configService.getBaseUrl() + '/ws-smart-home-register-endpoint';
  }


  public connect(): void {
    const jwtToken = this._authenticationService.getRawToken();
    const ws = new SockJS(this._webSocketEndPoint + `?Authorization=Bearer ${jwtToken}`);
    this._stompClient = Stomp.over(ws);
    this._stompClient.debug = null;
    const that = this;
    that._stompClient.connect({Authorization: 'Bearer ' + jwtToken}, (frame) => {
      that._stompClient.subscribe(that._previewUrl, (sdkEvent) => {
        that.onMessageReceived(sdkEvent);
      });
      this._initializePreview();
    }, this.errorCallBack.bind(this));
  }

  public disconnect(): void {
    if (this._stompClient != null) {
      this._stompClient.disconnect();
      this._stompClient = null;
    }
  }

  // on error, schedule a reconnection attempt
  private errorCallBack(error): void {
    if (this._failedAttempts >= this._failedAttemptsLimit) {
      this.disconnect();
    }
    setTimeout(() => {
      this._failedAttempts++;
      this.connect();
    }, this._connectionReattemptTimeout);
  }

  private onMessageReceived(response): void {
    this.imageSubject.next(JSON.parse(response.body));
  }

  public getImagePreviewSubscription(): Observable<ImageModel> {
    return this.imageSubject.asObservable();
  }

  private _initializePreview(): void {
    this._stompClient.send(this._previewInitializationUrl, {}, '');
  }
}
