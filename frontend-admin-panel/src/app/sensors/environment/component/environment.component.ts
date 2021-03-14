import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-environment',
  templateUrl: './environment.component.html',
  styleUrls: ['./environment.component.sass']
})
export class EnvironmentComponent implements OnInit {

  private _temperature: number = 1;
  private _humidity: number = 1;
  private _pressure: number = 1;

  get temperature() { return this._temperature; }
  get humidity() { return this._humidity; }
  get pressure() { return this._pressure; }

  constructor(private http: HttpClient) { }

  ngOnInit() {
  }

}
