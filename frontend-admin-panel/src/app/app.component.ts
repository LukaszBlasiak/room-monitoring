import {Component, OnDestroy, OnInit} from '@angular/core';
import {CameraMediumWebsocketService} from './sensors/camera-preview/service/CameraMediumWebsocket.service';
import {Subscription} from 'rxjs';
import {AuthenticationService} from './security/service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent {
}
