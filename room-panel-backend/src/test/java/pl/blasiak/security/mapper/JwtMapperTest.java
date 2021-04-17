package pl.blasiak.security.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.blasiak.application.config.ProfilesConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles(ProfilesConfig.PROFILE_LOCAL)
class JwtMapperTest {

    @Autowired
    private JwtMapper jwtMapper;


    @Test
    @DisplayName("Convert to JWT model - should return converted model")
    void getWeather_ShouldReturnResponse() {
        final var type = "HS512";
        final var token = "some very secret token";
        final var convertedModel = this.jwtMapper.toJwtResponse(type, token);
        assertEquals(type, convertedModel.getType());
        assertEquals(token, convertedModel.getToken());
    }
}
