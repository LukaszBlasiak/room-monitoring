import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {UserService} from '../../../security/service';
import {HttpClient} from '@angular/common/http';
import {CameraMediumWebsocketService} from '../../service/CameraMediumWebsocket.service';
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';

export interface ImageModel {
  bytesAsBase64: string;
  creationTime: any;
}

@Component({
  selector: 'app-medium-room-preview',
  templateUrl: './medium-room-preview.component.html',
  styleUrls: ['./medium-room-preview.component.sass']
})
export class MediumRoomPreviewComponent implements OnDestroy {

  currentUser: string;
  public imageSrc: SafeResourceUrl;
  public isPaused = false;
  private mediumRoomPreviewSubscription: Subscription;

  constructor(private userService: UserService, private http: HttpClient,
              private cameraMediumWebsocketService: CameraMediumWebsocketService, private sanitizer: DomSanitizer) {
    this.currentUser = localStorage.getItem('currentUser');
  }

  startPreview() {
    this.cameraMediumWebsocketService._connect();
    this.mediumRoomPreviewSubscription = this.cameraMediumWebsocketService.getImagePreviewSubscription()
      .subscribe((imageModel: ImageModel) => {
        if (!this.isPaused && imageModel) {
          this.imageSrc = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,'
            + imageModel.bytesAsBase64);
        }
      });
  }

  public pausePreview() {
    this.isPaused = !this.isPaused;
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
  }

  public isPreviewAvailable(): boolean {
    return this.imageSrc != null;
  }

}
