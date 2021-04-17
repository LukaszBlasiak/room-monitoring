package pl.blasiak.sensor.mapper;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import pl.blasiak.sensor.dto.WeatherModel;

@Component
public class WeatherMapperImpl implements WeatherMapper {

    private static final String MAIN_KEY = "main";
    private static final String TEMP_KEY = "temp";

    @Override
    public WeatherModel openWeatherResponseToModel(final String response) {
        final var json = new JSONObject(response);
        final var forecast = json.getJSONObject(MAIN_KEY);
        return WeatherModel.builder()
                .temperature(forecast.getFloat(TEMP_KEY) - 273.15f)
                .build();
    }
}
