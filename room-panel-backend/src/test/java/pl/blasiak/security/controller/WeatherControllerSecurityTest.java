package pl.blasiak.security.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.blasiak.application.config.ProfilesConfig;
import pl.blasiak.helper.db.init.UsersInit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(ProfilesConfig.PROFILE_LOCAL)
@UsersInit
class WeatherControllerSecurityTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getBme280Measurements_ShouldReturnMeasurements() throws Exception {
        this.mockMvc.perform(get("/api/weather"))
                .andExpect(status().is(401));
    }
}
