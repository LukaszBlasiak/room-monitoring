package pl.blasiak.security.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.blasiak.application.config.ProfilesConfig;
import pl.blasiak.security.entity.UserEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles(ProfilesConfig.PROFILE_LOCAL)
class UserDetailsMapperTest {

    @Autowired
    private UserDetailsMapper userDetailsMapper;


    @Test
    @DisplayName("Convert to SpringLoginDetails - should return converted model")
    void getWeather_ShouldReturnResponse() {
        final var username = "some ordinary user";
        final var password = "some very secret password";
        final var active = true;
        final var userEntity = new UserEntity(1L, username, active, password);
        final var convertedModel = this.userDetailsMapper.map(userEntity);
        assertEquals(username, convertedModel.getUsername());
        assertEquals(password, convertedModel.getPassword());
        assertEquals(active, convertedModel.isEnabled());
    }
}
