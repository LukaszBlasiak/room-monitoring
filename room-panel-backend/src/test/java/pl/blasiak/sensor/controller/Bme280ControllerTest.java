package pl.blasiak.sensor.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.blasiak.application.config.ProfilesConfig;
import pl.blasiak.helper.TestRequestFactory;
import pl.blasiak.helper.db.init.UsersInit;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(ProfilesConfig.PROFILE_LOCAL)
@UsersInit
public class Bme280ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TestRequestFactory testRequestFactory;

    @Test
    public void getBme280Measurements_ShouldReturnMeasurements() throws Exception {
        this.mockMvc.perform(testRequestFactory.get("/api/bme280"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.temperature").value(26.291994f))
                .andExpect(jsonPath("$.humidity").value(28.04638f))
                .andExpect(jsonPath("$.pressure").value(1005.5311f));
    }
}
