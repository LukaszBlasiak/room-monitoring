package pl.blasiak.sensor.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import pl.blasiak.application.config.OpenWeatherConfig;
import pl.blasiak.application.config.ProfilesConfig;
import pl.blasiak.common.util.ExternalApiUrl;
import pl.blasiak.common.util.ExternalApiUtil;
import pl.blasiak.sensor.dto.Bme280MeasurementsModel;
import pl.blasiak.sensor.exception.SensorException;

import java.io.IOException;
import java.util.Collections;

@Service
@Profile(ProfilesConfig.PROFILE_PROD)
public class Bme280UtilPythonImpl implements Bme280Util {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Float PRESSURE_CORRECTION_CONSTANT = 8.3f;
    private final ExternalApiUtil externalApiUtil;
    private final OpenWeatherConfig openWeatherConfig;

    public Bme280UtilPythonImpl(final ExternalApiUtil externalApiUtil, final OpenWeatherConfig openWeatherConfig) {
        this.externalApiUtil = externalApiUtil;
        this.openWeatherConfig = openWeatherConfig;
    }

    @Override
    public Bme280MeasurementsModel getBme280Measurements() {
        try {
            final var response = externalApiUtil.getRestCallResult(
                    ExternalApiUrl.BME280, HttpMethod.GET, Collections.emptyMap(), Bme280MeasurementsModel.class);
            response.setPressure(response.getPressure() + openWeatherConfig.getAltitude() / PRESSURE_CORRECTION_CONSTANT);
            return response;
        } catch (IOException | ResourceAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new SensorException(e.getMessage(), e);
        }
    }
}
