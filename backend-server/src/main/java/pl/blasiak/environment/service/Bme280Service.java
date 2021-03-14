package pl.blasiak.environment.service;

import pl.blasiak.environment.dto.Bme280MeasurementsModel;

public interface Bme280Service {

    Bme280MeasurementsModel getBme280Measurements();
}
