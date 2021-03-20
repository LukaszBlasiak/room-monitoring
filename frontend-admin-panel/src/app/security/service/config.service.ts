import {Injectable} from '@angular/core';
import {Config} from '../../common/config';
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
