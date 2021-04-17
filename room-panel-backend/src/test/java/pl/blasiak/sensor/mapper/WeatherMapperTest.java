package pl.blasiak.sensor.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.blasiak.application.config.ProfilesConfig;
import pl.blasiak.helper.OpenWeatherResponseGenerator;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles(ProfilesConfig.PROFILE_LOCAL)
class WeatherMapperTest {

    @Autowired
    private WeatherMapper weatherMapper;


    @Test
    @DisplayName("Convert weather forecast from OpenWeather API - should return converted forecast")
    void openWeatherResponseToModel_ShouldReturnConvertedModel() throws IOException {
        final var responseToMap = OpenWeatherResponseGenerator.prepareResponseModel(13.5f);

        final var forecast = this.weatherMapper.openWeatherResponseToModel(responseToMap);
        assertEquals(13.5f, forecast.getTemperature());
    }
}
