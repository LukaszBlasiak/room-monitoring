package pl.blasiak.application.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "open-weather")
@Getter
@Setter
public class OpenWeatherConfig {

    public static final String CITY_KEY = "id";
    public static final String AUTH_KEY = "appid";

    private Integer cityId;
    private String apiKey;
    private Float altitude;

}
