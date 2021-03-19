import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
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
  _isPlaying = false;
  _sizeInKb: string;
  public imageSrc: SafeResourceUrl;
  private _lastUpdateTimestamp: Date;
  private mediumRoomPreviewSubscription: Subscription;

  get isFullscreen(): boolean {
    return this._isFullscreen;
  }

  get lastUpdateTimestamp(): Date {
    return this._lastUpdateTimestamp;
  }

  get sizeInKb(): string {
    return this._sizeInKb;
  }

  constructor(private cameraMediumWebsocketService: CameraMediumWebsocketService, private sanitizer: DomSanitizer) {
  }

  startPreview() {
    this._isPlaying = true;
    this.cameraMediumWebsocketService._connect();
    this.mediumRoomPreviewSubscription = this.cameraMediumWebsocketService.getImagePreviewSubscription()
      .subscribe(
        (imageModel: ImageModel) => {
          if (imageModel) {
            this.imageSrc = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,'
              + imageModel.bytesAsBase64);
            this._lastUpdateTimestamp = new Date(imageModel.creationTime);
            this._sizeInKb = (imageModel.bytesAsBase64.length / 1000).toFixed(0);
          }
        },
        (error => {
          this._isPlaying = false;
        }));
  }

  ngOnDestroy() {
    this.stopPreview();
  }

  stopPreview() {
    this._isPlaying = false;
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

  public invertFullscreenState() {
    this._isFullscreen = !this._isFullscreen;
  }
}
