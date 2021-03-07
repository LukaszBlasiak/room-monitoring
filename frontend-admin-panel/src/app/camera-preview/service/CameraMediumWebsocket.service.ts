import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import {Injectable} from '@angular/core';
import {Observable, Subject, Subscription} from 'rxjs';
import {ConfigService} from '../../security/service/config.service';
import {ImageModel} from '../component/medium-room-preview/medium-room-preview.component';

@Injectable()
export class CameraMediumWebsocketService {
  webSocketEndPoint;
  previewUrl = '/user/preview/medium';
  previewInitializationUrl = '/ws/preview/medium/start';
  stompClient: any;
  connectionReattemptTimeout = 5000;

  private imageSubject = new Subject<ImageModel>();

  constructor(private configService: ConfigService) {
    this.webSocketEndPoint = configService.getBaseUrl() + '/ws-smart-home-register-endpoint';
  }


  _connect() {
    const ws = new SockJS(this.webSocketEndPoint);
    this.stompClient = Stomp.over(ws);
    const that = this;
    that.stompClient.connect({'X-Requested-With': 'XMLHttpRequest'}, (frame) => {
      that.stompClient.subscribe(that.previewUrl, (sdkEvent) => {
        that.onMessageReceived(sdkEvent);
      });
      this._initializePreview();
      // _this.stompClient.reconnect_delay = 2000;
    }, this.errorCallBack);
  }

  _disconnect() {
    if (this.stompClient != null) {
      // this.stompClient.send('/preview/medium/stop', {});
      this.stompClient.disconnect();
      this.stompClient = null;
    }
  }

  // on error, schedule a reconnection attempt
  private errorCallBack(error) {
    setTimeout(() => {
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
