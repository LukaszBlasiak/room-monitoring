package pl.blasiak.sensor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Model representing a weather forecast.")
public class WeatherModel {

    @Schema(description = "Temperature outside obtained from OpenWeatherAPI.")
    private Float temperature;
}
