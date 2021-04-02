package pl.blasiak.sensor.util;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import pl.blasiak.application.config.OpenWeatherConfig;
import pl.blasiak.common.util.ExternalApiUrl;
import pl.blasiak.common.util.ExternalApiUtil;
import pl.blasiak.sensor.dto.WeatherModel;
import pl.blasiak.sensor.exception.SensorException;
import pl.blasiak.sensor.mapper.WeatherMapper;

import java.io.IOException;
import java.net.URL;

@Component
public class OpenWeatherUtilImpl implements OpenWeatherUtil {

    private final OpenWeatherConfig openWeatherConfig;
    private final ExternalApiUtil externalApiUtil;
    private final WeatherMapper weatherMapper;
    private final static String CITY_KEY = "id";
    private final static String AUTH_KEY = "appid";

    public OpenWeatherUtilImpl(final OpenWeatherConfig openWeatherConfig, final ExternalApiUtil externalApiUtil,
                               final WeatherMapper weatherMapper) {
        this.openWeatherConfig = openWeatherConfig;
        this.externalApiUtil = externalApiUtil;
        this.weatherMapper = weatherMapper;
    }

    @Override
    public WeatherModel getWeather() {
        try {
            final URL finalUrl = new URL(String.format("%s?%s=%s&%s=%s", ExternalApiUrl.OPENWEATHER.getUrl(), CITY_KEY,
                    openWeatherConfig.getCityId(), AUTH_KEY, openWeatherConfig.getApiKey()));
            final var response = this.externalApiUtil.getRestCallResultAsString(finalUrl, HttpMethod.GET);

            return this.weatherMapper.openweatherToModel(response);
        } catch (IOException e) {
            throw new SensorException(e.getMessage(), e);
        }
    }
}
