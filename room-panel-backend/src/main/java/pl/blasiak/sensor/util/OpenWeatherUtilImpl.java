package pl.blasiak.sensor.util;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import pl.blasiak.application.config.OpenWeatherConfig;
import pl.blasiak.common.util.ExternalApiUrl;
import pl.blasiak.common.util.ExternalApiUtil;
import pl.blasiak.sensor.dto.WeatherModel;
import pl.blasiak.sensor.exception.SensorException;
import pl.blasiak.sensor.mapper.WeatherMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class OpenWeatherUtilImpl implements OpenWeatherUtil {

    private final ExternalApiUtil externalApiUtil;
    private final WeatherMapper weatherMapper;
    private final URL finalUrl;
    private static final String URL_FORMAT = "%s?%s=%s&%s=%s";

    public OpenWeatherUtilImpl(final OpenWeatherConfig openWeatherConfig, final ExternalApiUtil externalApiUtil,
                               final WeatherMapper weatherMapper) {
        this.externalApiUtil = externalApiUtil;
        this.weatherMapper = weatherMapper;
        try {
            this.finalUrl = new URL(String.format(URL_FORMAT, ExternalApiUrl.OPEN_WEATHER.getUrl(), OpenWeatherConfig.CITY_KEY,
                    openWeatherConfig.getCityId(), OpenWeatherConfig.AUTH_KEY, openWeatherConfig.getApiKey()));
        } catch (MalformedURLException e) {
            throw new SensorException(e.getMessage(), e);
        }
    }

    @Override
    public WeatherModel getWeather() {
        try {
            final var response = this.externalApiUtil.getRestCallResultAsString(finalUrl, HttpMethod.GET);
            return this.weatherMapper.openWeatherResponseToModel(response);
        } catch (IOException | ResourceAccessException e) {
            throw new SensorException(e.getMessage(), e);
        }
    }
}
