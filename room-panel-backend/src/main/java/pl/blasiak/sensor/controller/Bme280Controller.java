package pl.blasiak.sensor.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(description = "Set of endpoints for Retrieving the BME280 measurements.")
public class Bme280Controller {

    private final Bme280Util bme280Util;

    @GetMapping(value = "/bme280", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Returns a single BME280 measurement.")
    public ResponseEntity<Bme280MeasurementsModel> getBme280Measurements() {
        return ResponseEntity.ok(bme280Util.getBme280Measurements());
    }

}
