package pl.blasiak.camera.exception;

public class CameraException extends RuntimeException {

    public CameraException(String message, Throwable cause) {
        super(message, cause);
    }

    public CameraException(String message) {
        super(message);
    }
}
