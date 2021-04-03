package pl.blasiak.sensor.mapper;

import pl.blasiak.sensor.dto.WeatherModel;

public interface WeatherMapper {

    /**
     * Converts plain JSON response from OpenWeather service to frontend model.
     *
     * @param response plain JSON response as string from OpenWeather service
     * @return Converted response to frontend model
     */
    WeatherModel openWeatherResponseToModel(final String response);
}
