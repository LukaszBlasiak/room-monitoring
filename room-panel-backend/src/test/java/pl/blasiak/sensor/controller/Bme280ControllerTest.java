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
import pl.blasiak.sensor.dto.Bme280MeasurementsModel;
import pl.blasiak.sensor.exception.SensorException;
import pl.blasiak.sensor.util.Bme280Util;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(ProfilesConfig.PROFILE_LOCAL)
@UsersInit
class Bme280ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    Bme280Util bme280Util;

    @Autowired
    TestRequestFactory testRequestFactory;

    @Test
    @DisplayName("Get BME280 measurements - should return measurements")
    void getBme280Measurements_ShouldReturnMeasurements() throws Exception {
        Mockito.when(this.bme280Util.getBme280Measurements()).thenReturn(
                new Bme280MeasurementsModel(26.291994f, 28.04638f, 1005.5311f));
        this.mockMvc.perform(testRequestFactory.get("/api/bme280"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.temperature").value(26.291994f))
                .andExpect(jsonPath("$.humidity").value(28.04638f))
                .andExpect(jsonPath("$.pressure").value(1005.5311f));
    }

    @Test
    @DisplayName("Get BME280 measurements - - connection timeout - should throw exception")
    void getBme280Measurements_ConnectionTimedOut_ShouldThrowSensorException() throws Exception {
        Mockito.when(this.bme280Util.getBme280Measurements())
                .thenThrow(new SensorException("Connect timed out"));

        this.mockMvc.perform(testRequestFactory.get("/api/bme280"))
                .andExpect(status().is(500));
    }
}
