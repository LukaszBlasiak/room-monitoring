package pl.blasiak.environment.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.blasiak.environment.dto.Bme280MeasurementsModel;
import pl.blasiak.environment.service.Bme280Service;


@RestController()
@RequestMapping("/api")
@AllArgsConstructor
@Api(description = "Set of endpoints for Retrieving the BME280 measurements.")
public class Bme280Controller {

    private final Bme280Service bme280Service;

    @PostMapping(value = "/bme280", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Returns a single BME280 measurement.")
    public ResponseEntity<Bme280MeasurementsModel> getBme280Measurements() {
        return ResponseEntity.ok(bme280Service.getBme280Measurements());
    }

}
