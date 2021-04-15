package pl.blasiak.sensor.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.blasiak.sensor.dto.WeatherModel;
import pl.blasiak.sensor.util.OpenWeatherUtil;


@RestController()
@RequestMapping("/api")
@AllArgsConstructor
@Api(description = "Set of endpoints for retrieving the weather forecast.")
public class WeatherController {

    private final OpenWeatherUtil openWeatherUtil;

    @GetMapping(value = "/weather", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Returns a single BME280 measurement.")
    public ResponseEntity<WeatherModel> getBme280Measurements() {
        return ResponseEntity.ok(openWeatherUtil.getWeather());
    }

}
