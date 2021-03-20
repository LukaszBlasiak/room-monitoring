import {Component, OnDestroy} from '@angular/core';
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
  private _imageSrc: SafeResourceUrl;
  private _lastUpdateTimestamp: Date;
  private _mediumRoomPreviewSubscription: Subscription;

  get imageSrc(): SafeResourceUrl {
    return this._imageSrc;
  }

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

  startPreview(): void {
    this._isPlaying = true;
    this.cameraMediumWebsocketService.connect();
    this._mediumRoomPreviewSubscription = this.cameraMediumWebsocketService.getImagePreviewSubscription()
      .subscribe(
        (imageModel: ImageModel) => {
          if (imageModel) {
            this._imageSrc = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,'
              + imageModel.bytesAsBase64);
            this._lastUpdateTimestamp = new Date(imageModel.creationTime);
            this._sizeInKb = (imageModel.bytesAsBase64.length / 1000).toFixed(0);
          }
        },
        (error => {
          this._isPlaying = false;
        }));
  }

  ngOnDestroy(): void {
    this.stopPreview();
  }

  public stopPreview(): void {
    this._isPlaying = false;
    this._isFullscreen = false;
    this.cameraMediumWebsocketService.disconnect();
    if (this._mediumRoomPreviewSubscription != null && !this._mediumRoomPreviewSubscription.closed) {
      this._mediumRoomPreviewSubscription.unsubscribe();
      this._mediumRoomPreviewSubscription = null;
    }
    this._imageSrc = null;
    this._lastUpdateTimestamp = null;
  }

  public isPreviewAvailable(): boolean {
    return this._imageSrc != null;
  }

  public invertFullscreenState(): void {
    this._isFullscreen = !this._isFullscreen;
  }
}
