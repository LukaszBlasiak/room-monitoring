import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {UserService} from '../../../../security/service';
import {HttpClient} from '@angular/common/http';
import {CameraMediumWebsocketService} from '../../service/CameraMediumWebsocket.service';
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';

export interface ImageModel {
  bytesAsBase64: string;
  creationTime: Date;
}

@Component({
  selector: 'app-medium-room-preview',
  templateUrl: './medium-room-preview.component.html',
  styleUrls: ['./medium-room-preview.component.sass']
})
export class MediumRoomPreviewComponent implements OnDestroy {

  _isFullscreen = false;
  public imageSrc: SafeResourceUrl;
  private _lastUpdateTimestamp: Date;
  private mediumRoomPreviewSubscription: Subscription;

  get isFullscreen() { return this._isFullscreen; }
  get lastUpdateTimestamp() { return this._lastUpdateTimestamp; }

  constructor(private userService: UserService, private http: HttpClient,
              private cameraMediumWebsocketService: CameraMediumWebsocketService, private sanitizer: DomSanitizer) {
  }

  startPreview() {
    this.cameraMediumWebsocketService._connect();
    this.mediumRoomPreviewSubscription = this.cameraMediumWebsocketService.getImagePreviewSubscription()
      .subscribe((imageModel: ImageModel) => {
        if (imageModel) {
          this.imageSrc = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,'
            + imageModel.bytesAsBase64);
          this._lastUpdateTimestamp = new Date(imageModel.creationTime);
        }
      });
  }

  ngOnDestroy() {
    this.stopPreview();
  }

  stopPreview() {
    this.cameraMediumWebsocketService._disconnect();
    if (this.mediumRoomPreviewSubscription != null && !this.mediumRoomPreviewSubscription.closed) {
      this.mediumRoomPreviewSubscription.unsubscribe();
      this.mediumRoomPreviewSubscription = null;
    }
    this.imageSrc = null;
    this._lastUpdateTimestamp = null;
  }

  public isPreviewAvailable(): boolean {
    return this.imageSrc != null;
  }

  public invertFullscreenState() { this._isFullscreen = !this._isFullscreen; }
}
