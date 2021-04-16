package pl.blasiak.sensor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.blasiak.sensor.dto.Bme280MeasurementsModel;
import pl.blasiak.sensor.util.Bme280Util;


@RestController()
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name = "bme280", description = "Set of endpoints for Retrieving the BME280 measurements.")
public class Bme280Controller {

    private final Bme280Util bme280Util;

    @GetMapping(value = "/bme280", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Returns a single BME280 measurement.")
    public ResponseEntity<Bme280MeasurementsModel> getBme280Measurements() {
        return ResponseEntity.ok(bme280Util.getBme280Measurements());
    }

}
