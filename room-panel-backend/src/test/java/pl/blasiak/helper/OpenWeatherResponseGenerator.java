package pl.blasiak.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

public class OpenWeatherResponseGenerator {
    @AllArgsConstructor
    @Data
    static class TempForecast {
        private final Float temp;
    }
    @AllArgsConstructor
    @Data
    static class OpenWeatherResponse {
        private final TempForecast main;
    }

    public static String prepareResponseModel(final Float temp) throws JsonProcessingException {
        final var tempInKelvin = temp + 273.15f;
        final var tempForecast = new TempForecast(tempInKelvin);
        final var openWeatherResponse = new OpenWeatherResponse(tempForecast);
        return new ObjectMapper().writeValueAsString(openWeatherResponse);
    }

}
