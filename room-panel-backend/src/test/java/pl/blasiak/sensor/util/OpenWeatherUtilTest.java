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
import pl.blasiak.common.util.ExternalApiUtilImpl;
import pl.blasiak.helper.OpenWeatherResponseGenerator;
import pl.blasiak.sensor.exception.SensorException;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
@ActiveProfiles(ProfilesConfig.PROFILE_LOCAL)
class OpenWeatherUtilTest {

    @MockBean
    private ExternalApiUtilImpl pythonApiUtil;
    @Autowired
    private OpenWeatherUtil openWeatherUtil;


    @Test
    @DisplayName("Get weather forecast from OpenWeather API - should return forecast")
    void getWeather_ShouldReturnResponse() throws IOException {
        Mockito.when(this.pythonApiUtil.getRestCallResultAsString(any(URL.class), eq(HttpMethod.GET)))
                .thenReturn(OpenWeatherResponseGenerator.prepareResponseModel(13.5f));

        final var forecast = this.openWeatherUtil.getWeather();
        assertEquals(13.5f, forecast.getTemperature());
    }

    @Test
    @DisplayName("Get weather forecast from OpenWeather API - connection timeout - should throw exception")
    void getWeather_ConnectionTimedOut_ShouldThrowSensorException() throws IOException {
        Mockito.when(this.pythonApiUtil.getRestCallResultAsString(any(URL.class), eq(HttpMethod.GET)))
                .thenThrow(new ResourceAccessException("Connect timed out"));

        assertThrows(SensorException.class, () -> this.openWeatherUtil.getWeather());
    }

    @Test
    @DisplayName("Get weather forecast from OpenWeather API - Incorrect URL - should throw sensor exception")
    void getWeather_IncorrectUrl_ShouldThrowSensorException() throws IOException {
        Mockito.when(this.pythonApiUtil.getRestCallResultAsString(any(URL.class), eq(HttpMethod.GET)))
                .thenThrow(new IOException("Incorrect URL"));

        assertThrows(SensorException.class, () -> this.openWeatherUtil.getWeather());
    }
}
