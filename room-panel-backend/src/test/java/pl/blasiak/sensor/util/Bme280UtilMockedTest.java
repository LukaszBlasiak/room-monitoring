package pl.blasiak.sensor.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.blasiak.application.config.ProfilesConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles(ProfilesConfig.PROFILE_LOCAL)
class Bme280UtilMockedTest {

    @Autowired
    private Bme280Util bme280Util;

    @Test
    @DisplayName("Get BME280 measurements from mocked instance - should return measurements")
    void getBme280Measurements_ShouldReturnMeasurements() {
        final var measurements = this.bme280Util.getBme280Measurements();
        assertEquals(26.291994f, measurements.getTemperature());
        assertEquals(28.04638f, measurements.getHumidity());
        assertEquals(1005.5311f, measurements.getPressure());
    }
}
