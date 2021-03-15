package pl.blasiak.environment.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import pl.blasiak.application.exception.CameraException;
import pl.blasiak.common.util.PythonApiUrl;
import pl.blasiak.common.util.PythonApiUtil;
import pl.blasiak.environment.dto.Bme280MeasurementsModel;

import java.io.IOException;
import java.util.Collections;

@Service
@Profile("prod")
public class Bme280ServicePythonImpl implements Bme280Service {

    private static final Logger LOGGER = LogManager.getLogger();
    private final PythonApiUtil pythonApiUtil;

    public Bme280ServicePythonImpl(final PythonApiUtil pythonApiUtil) {
        this.pythonApiUtil = pythonApiUtil;
    }

    @Override
    public Bme280MeasurementsModel getBme280Measurements() {
        try {
            return pythonApiUtil.getRestCallResult(PythonApiUrl.BME280, HttpMethod.GET, Collections.emptyMap(), Bme280MeasurementsModel.class);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CameraException(e.getMessage(), e);
        }
    }
}
