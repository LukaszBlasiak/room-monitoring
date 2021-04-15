package pl.blasiak.common.dto;

import lombok.Getter;

public enum ErrorCode {

    INTERNAL_01("An internal error occurred. Please try again later."),

    AUTH_01("Incorrect credentials."),
    AUTH_02("Given user has been disabled."),
    AUTH_03("Given JWT is invalid or expired."),

    CAMERA_01("An unexpected error occurred while trying attempt raspberry camera. Please try again later."),

    SENSOR_01("An unexpected error occurred while trying attempt BME280 sensor. Please try again later.");


    ErrorCode(final String message) {
        this.message = message;
    }

    @Getter
    String message;


}
