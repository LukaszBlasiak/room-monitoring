import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CameraMediumWebsocketService} from './camera-preview/service/CameraMediumWebsocket.service';
import {FormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AlertComponent} from './security/directive';
import {LoginComponent} from './security/login';
import {RegisterComponent} from './security/register';
import {HomeComponent} from './main/component/home';
import {AuthGuard} from './security/guard';
import {AlertService, AuthenticationService, UserService} from './security/service';
import {UnauthorizedInterceptor} from './security/interceptor/UnauthorizedInterceptor';
import { MediumRoomPreviewComponent } from './camera-preview/component/medium-room-preview/medium-room-preview.component';

@NgModule({
  declarations: [
    AppComponent,
    AlertComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    MediumRoomPreviewComponent
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
    UserService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: UnauthorizedInterceptor,
      multi: true
    }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
