package pl.blasiak.sensor.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
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
    private final ExternalApiUtil externalApiUtil;

    public Bme280UtilPythonImpl(final ExternalApiUtil externalApiUtil) {
        this.externalApiUtil = externalApiUtil;
    }

    @Override
    public Bme280MeasurementsModel getBme280Measurements() {
        try {
            return externalApiUtil.getRestCallResult(ExternalApiUrl.BME280, HttpMethod.GET, Collections.emptyMap(), Bme280MeasurementsModel.class);
        } catch (IOException | ResourceAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new SensorException(e.getMessage(), e);
        }
    }
}
