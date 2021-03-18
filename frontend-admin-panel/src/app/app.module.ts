import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CameraMediumWebsocketService} from './sensors/camera-preview/service/CameraMediumWebsocket.service';
import {FormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AlertComponent} from './security/directive';
import {LoginComponent} from './security/login';
import {HomeComponent} from './main/component/home';
import {AuthGuard} from './security/guard';
import {UnauthorizedInterceptor} from './security/interceptor/UnauthorizedInterceptor';
import { MediumRoomPreviewComponent } from './sensors/camera-preview/component/medium-room-preview/medium-room-preview.component';
import {ConfigService} from './security/service/config.service';
import { EnvironmentComponent } from './sensors/environment/component/environment.component';
import {AlertService} from './security/service/alert.service';
import {AuthenticationService} from './security/service/authentication.service';

@NgModule({
  declarations: [
    AppComponent,
    AlertComponent,
    HomeComponent,
    LoginComponent,
    MediumRoomPreviewComponent,
    EnvironmentComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [CameraMediumWebsocketService,
    AuthGuard,
    AlertService,
    AuthenticationService,
    ConfigService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: UnauthorizedInterceptor,
      multi: true
    }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
