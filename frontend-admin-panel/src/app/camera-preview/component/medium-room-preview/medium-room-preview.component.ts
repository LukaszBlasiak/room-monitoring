import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {UserService} from '../../../security/service';
import {HttpClient} from '@angular/common/http';
import {CameraMediumWebsocketService} from '../../service/CameraMediumWebsocket.service';

@Component({
  selector: 'app-medium-room-preview',
  templateUrl: './medium-room-preview.component.html',
  styleUrls: ['./medium-room-preview.component.sass']
})
export class MediumRoomPreviewComponent implements OnDestroy {

  currentUser: string;
  public imageSrc: string;
  public isPaused = false;
  private mediumRoomPreviewSubscription: Subscription;

  constructor(private userService: UserService, private http: HttpClient,
              private cameraMediumWebsocketService: CameraMediumWebsocketService) {
    this.currentUser = localStorage.getItem('currentUser');
  }

  startPreview() {
    this.cameraMediumWebsocketService._connect();
    this.mediumRoomPreviewSubscription = this.cameraMediumWebsocketService.getImagePreviewSubscription().subscribe((imageSrc) => {
      if (!this.isPaused) {
        this.imageSrc = imageSrc;
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
