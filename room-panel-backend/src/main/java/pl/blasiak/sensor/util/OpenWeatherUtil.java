package pl.blasiak.sensor.util;

import pl.blasiak.sensor.dto.WeatherModel;

public interface OpenWeatherUtil {

    /**
     * Reads current weather forecast from openweathermap.org service.
     *
     * @return Converted forecast into frontend model.
     */
    WeatherModel getWeather();
}
