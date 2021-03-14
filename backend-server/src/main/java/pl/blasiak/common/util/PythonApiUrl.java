package pl.blasiak.common.util;

import lombok.Getter;

public enum PythonApiUrl {

    MEDIUM_ROOM_PREVIEW("/cameraPreview"),
    BME280("/bme280");

    @Getter
    private final String url;

    PythonApiUrl(final String url) {
        this.url = url;
    }
}
