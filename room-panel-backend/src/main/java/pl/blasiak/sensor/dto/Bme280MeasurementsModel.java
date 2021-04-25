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
    @Schema(description = "Room temperature obtained from BME280 sensor, in Celsius unit.")
    private Float temperature;
    @Schema(description = "Room humidity obtained from BME280 sensor, in %RH unit.")
    private Float humidity;
    @Schema(description = "Atmospheric  pressure obtained from BME280 sensor, converted to absolute pressure according" +
            " to given altitude in property file.")
    private Float pressure;
}
