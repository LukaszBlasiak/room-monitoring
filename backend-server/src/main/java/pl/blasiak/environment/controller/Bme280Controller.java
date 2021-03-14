package pl.blasiak.environment.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.blasiak.environment.dto.Bme280MeasurementsModel;
import pl.blasiak.environment.service.Bme280Service;


@RestController("/api")
@RequestMapping("/api")
@AllArgsConstructor
public class Bme280Controller {

    private final Bme280Service bme280Service;

    @RequestMapping(value = "/bme280", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Bme280MeasurementsModel> getBme280Measurements() {
        return ResponseEntity.ok(bme280Service.getBme280Measurements());
    }

}
