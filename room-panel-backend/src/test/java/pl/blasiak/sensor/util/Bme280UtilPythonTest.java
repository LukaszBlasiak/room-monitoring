package pl.blasiak.sensor.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.ResourceAccessException;
import pl.blasiak.application.config.ProfilesConfig;
import pl.blasiak.common.util.ExternalApiUrl;
import pl.blasiak.common.util.ExternalApiUtilImpl;
import pl.blasiak.sensor.dto.Bme280MeasurementsModel;
import pl.blasiak.sensor.exception.SensorException;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
@ActiveProfiles(ProfilesConfig.PROFILE_PROD)
class Bme280UtilPythonTest {

    @MockBean
    private ExternalApiUtilImpl pythonApiUtil;
    @Autowired
    private Bme280Util bme280Util;


    private Bme280MeasurementsModel prepareResponseModel() {
        return Bme280MeasurementsModel.builder().temperature(1.23f).humidity(4.56f).pressure(7.89f).build();
    }

    @Test
    @DisplayName("Get BME280 measurements from python API - should return measurements")
    void getBme280Measurements_ShouldReturnMeasurementsModel() throws IOException {
        Mockito.when(this.pythonApiUtil.getRestCallResult(any(ExternalApiUrl.class), eq(HttpMethod.GET), any(Map.class), any(Class.class)))
                .thenReturn(this.prepareResponseModel());

        final var measurements = this.bme280Util.getBme280Measurements();
        assertNotNull(measurements);
        assertEquals(1.23f, measurements.getTemperature());
        assertEquals(4.56f, measurements.getHumidity());
        assertEquals(38.853855f, measurements.getPressure());
    }

    @Test
    @DisplayName("Get BME280 measurements from python API - connection timed out - should throw sensor exception")
    void getBme280Measurements_ConnectionTimedOut_ShouldThrowSensorException() throws IOException {
        Mockito.when(this.pythonApiUtil.getRestCallResult(any(ExternalApiUrl.class), eq(HttpMethod.GET), any(Map.class), any(Class.class)))
                .thenThrow(new ResourceAccessException("Connect timed out"));

        assertThrows(SensorException.class, () -> this.bme280Util.getBme280Measurements());
    }

    @Test
    @DisplayName("Get BME280 measurements from python API - incorrect URL - should throw sensor exception")
    void getBme280Measurements_IncorrectUrl_ShouldThrowSensorException() throws IOException {
        Mockito.when(this.pythonApiUtil.getRestCallResult(any(ExternalApiUrl.class), eq(HttpMethod.GET), any(Map.class), any(Class.class)))
                .thenThrow(new IOException("Incorrect URL"));

        assertThrows(SensorException.class, () -> this.bme280Util.getBme280Measurements());
    }
}
