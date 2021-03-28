package pl.blasiak.sensor.exception;

public class SensorException extends RuntimeException {

    public SensorException(String message, Throwable cause) {
        super(message, cause);
    }

    public SensorException(String message) {
        super(message);
    }
}
