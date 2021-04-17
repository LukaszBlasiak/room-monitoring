package pl.blasiak.sensor.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.blasiak.application.config.ProfilesConfig;
import pl.blasiak.helper.TestRequestFactory;
import pl.blasiak.helper.db.init.UsersInit;
import pl.blasiak.sensor.dto.WeatherModel;
import pl.blasiak.sensor.exception.SensorException;
import pl.blasiak.sensor.util.OpenWeatherUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(ProfilesConfig.PROFILE_LOCAL)
@UsersInit
class WeatherControllerTest {

    @MockBean
    OpenWeatherUtil openWeatherUtil;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TestRequestFactory testRequestFactory;

    @Test
    void getWeather_ShouldReturnWeatherForecast() throws Exception {
        Mockito.when(this.openWeatherUtil.getWeather()).thenReturn(new WeatherModel(13.5f));
        this.mockMvc.perform(testRequestFactory.get("/api/weather"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.temperature").value(13.5f));
    }

    @Test
    @DisplayName("Get weather forecast from OpenWeather API - connection timeout - should throw exception")
    void getWeather_ConnectionTimedOut_ShouldThrowSensorException() throws Exception {
        Mockito.when(this.openWeatherUtil.getWeather())
                .thenThrow(new SensorException("Connect timed out"));

        this.mockMvc.perform(testRequestFactory.get("/api/weather"))
                .andExpect(status().is(500));
    }

}
