package pl.blasiak.sensor.mapper;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import pl.blasiak.sensor.dto.WeatherModel;

@Component
public class WeatherMapperImpl implements WeatherMapper {

    @Override
    public WeatherModel openWeatherResponseToModel(final String response) {
        final var json = new JSONObject(response);
        final var forecast = json.getJSONObject("main");
        return WeatherModel.builder()
                .temperature(forecast.getFloat("temp") - 273.15f)
                .build();
    }
}
