package pl.blasiak.environment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bme280MeasurementsModel {
    private Float temperature;
    private Float humidity;
    private Float pressure;
}
