import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../../../security/service/config.service';

interface Bme280MeasurementsModel {
  temperature: number;
  humidity: number;
  pressure: number;
}

interface WeatherModel {
  temperature: number;
}

@Component({
  selector: 'app-environment',
  templateUrl: './environment.component.html',
  styleUrls: ['./environment.component.sass']
})
export class EnvironmentComponent implements OnInit {

  private _temperature: string;
  private _weatherTemperature: string;
  private _humidity: string;
  private _pressure: string;
  private _bme280MeasurementEndpoint: string;
  private _weatherEndpoint: string;

  constructor(private _http: HttpClient, private configService: ConfigService) {
    this._bme280MeasurementEndpoint = configService.getBaseUrl() + '/api/bme280';
    this._weatherEndpoint = configService.getBaseUrl() + '/api/weather';
  }

  get temperature() {
    return this._temperature;
  }

  get weatherTemperature(): string {
    return this._weatherTemperature;
  }

  get humidity() {
    return this._humidity;
  }

  get pressure() {
    return this._pressure;
  }


  ngOnInit(): void {
    this._getMeasurements();
  }

  private _getMeasurements(): void {
    this._http.get(this._bme280MeasurementEndpoint).subscribe(
      (model: Bme280MeasurementsModel) => {
        this._temperature = model.temperature.toFixed(1);
        this._humidity = model.humidity.toFixed(1);
        this._pressure = model.pressure.toFixed(1);
      });
    this._http.get(this._weatherEndpoint).subscribe((response: WeatherModel) => {
      this._weatherTemperature = response.temperature.toFixed(1);
    });
  }
}
