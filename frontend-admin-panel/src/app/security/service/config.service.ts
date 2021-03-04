import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {Config} from '../../common/config';
import {User} from '../model';
import {isDevMode} from '@angular/core';

@Injectable()
export class ConfigService {

  public getBaseUrl(): string {
    if (isDevMode()) {
      return Config.LOCAL_API_BASE_URL;
    } else {
      return Config.PROD_API_BASE_URL;
    }
  }


}
