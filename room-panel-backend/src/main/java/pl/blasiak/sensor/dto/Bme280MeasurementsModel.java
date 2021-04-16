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
@Schema(description = "Model representing a single BME280 measurement which consists of temperature, humidity and " +
        "pressure measurement.")
public class Bme280MeasurementsModel {
    private Float temperature;
    private Float humidity;
    private Float pressure;
}
