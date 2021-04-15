package pl.blasiak.sensor.util;

import pl.blasiak.sensor.dto.Bme280MeasurementsModel;
import pl.blasiak.sensor.exception.SensorException;

public interface Bme280Util {

    /**
     * Reads measurements (temperature, humidity and pressure) from Raspberry Pi BME280 sensor and converts it into
     * {@link Bme280MeasurementsModel} model.
     *
     * @return {@link Bme280MeasurementsModel} model read from BME280 sensor.
     * @throws SensorException could not access BME280 sensor or some I/O error occurred
     */
    Bme280MeasurementsModel getBme280Measurements() throws SensorException;
}
