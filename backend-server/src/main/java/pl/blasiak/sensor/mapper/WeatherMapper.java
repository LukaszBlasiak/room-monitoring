package pl.blasiak.sensor.mapper;

import org.json.JSONObject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.blasiak.sensor.dto.WeatherModel;

public interface WeatherMapper {

    /**
     *
     * @param response
     * @return
     */
    WeatherModel openweatherToModel(final String response);
}
