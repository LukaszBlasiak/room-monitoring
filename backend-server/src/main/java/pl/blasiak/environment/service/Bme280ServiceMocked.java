package pl.blasiak.environment.service;

import org.springframework.context.annotation.Profile;
import pl.blasiak.environment.dto.Bme280MeasurementsModel;

@Profile("local")
//@Service
public class Bme280ServiceMocked implements Bme280Service {
    @Override
    public Bme280MeasurementsModel getBme280Measurements() {
        return Bme280MeasurementsModel.builder()
                .temperature(26.291994f)
                .humidity(28.04638f)
                .pressure(1005.5311f)
                .build();
    }
}
