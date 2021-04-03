package pl.blasiak.sensor.util;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.blasiak.application.config.ProfilesConfig;
import pl.blasiak.sensor.dto.Bme280MeasurementsModel;

@Profile(ProfilesConfig.PROFILE_LOCAL)
@Service
public class Bme280UtilMocked implements Bme280Util {

    @Override
    public Bme280MeasurementsModel getBme280Measurements() {
        return Bme280MeasurementsModel.builder()
                .temperature(26.291994f)
                .humidity(28.04638f)
                .pressure(1005.5311f)
                .build();
    }
}
