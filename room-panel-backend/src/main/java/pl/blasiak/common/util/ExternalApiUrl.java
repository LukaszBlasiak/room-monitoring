package pl.blasiak.common.util;

import lombok.Getter;

public enum ExternalApiUrl {

    ROOM_PREVIEW("/cameraPreview"),
    BME280("/bme280"),
    OPEN_WEATHER("https://api.openweathermap.org/data/2.5/weather");

    @Getter
    private final String url;

    ExternalApiUrl(final String url) {
        this.url = url;
    }
}
