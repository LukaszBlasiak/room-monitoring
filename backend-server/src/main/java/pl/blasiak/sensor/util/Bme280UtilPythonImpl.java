package pl.blasiak.sensor.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import pl.blasiak.application.config.ProfilesConfig;
import pl.blasiak.common.util.PythonApiUrl;
import pl.blasiak.common.util.PythonApiUtil;
import pl.blasiak.sensor.dto.Bme280MeasurementsModel;
import pl.blasiak.sensor.exception.SensorException;

import java.io.IOException;
import java.util.Collections;

@Service
@Profile(ProfilesConfig.PROFILE_PROD)
public class Bme280UtilPythonImpl implements Bme280Util {

    private static final Logger LOGGER = LogManager.getLogger();
    private final PythonApiUtil pythonApiUtil;

    public Bme280UtilPythonImpl(final PythonApiUtil pythonApiUtil) {
        this.pythonApiUtil = pythonApiUtil;
    }

    @Override
    public Bme280MeasurementsModel getBme280Measurements() {
        try {
            return pythonApiUtil.getRestCallResult(PythonApiUrl.BME280, HttpMethod.GET, Collections.emptyMap(), Bme280MeasurementsModel.class);
        } catch (IOException | ResourceAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new SensorException(e.getMessage(), e);
        }
    }
}
