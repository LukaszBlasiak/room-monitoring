package pl.blasiak.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.blasiak.application.config.ProfilesConfig;
import pl.blasiak.helper.db.init.UsersInit;
import pl.blasiak.security.model.JwtRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.blasiak.helper.TestRequestFactory.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(ProfilesConfig.PROFILE_LOCAL)
@UsersInit
class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void login_CorrectCredentials_ShouldLogIn() throws Exception {
        final var jwtRequest = new JwtRequest(USERNAME, PASSWORD);
        this.mockMvc.perform(post("/api/auth/logon").contentType(MediaType.APPLICATION_JSON).content(asJsonString(jwtRequest)))
                .andExpect(status().is(200));
    }

    @Test
    void login_IncorrectCredentials_ShouldLogIn() throws Exception {
        final var jwtRequest = new JwtRequest("incorrect", "credential");
        this.mockMvc.perform(post("/api/auth/logon").contentType(MediaType.APPLICATION_JSON).content(asJsonString(jwtRequest)))
                .andExpect(status().is(401));
    }
}
