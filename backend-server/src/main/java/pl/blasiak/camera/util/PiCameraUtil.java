package pl.blasiak.camera.util;

import pl.blasiak.application.exception.CameraException;
import pl.blasiak.camera.model.ImageModel;

import java.time.LocalDateTime;

public interface PiCameraUtil {

    /**
     * Reads image from Raspberry Pi camera and conterts it into base64 format.
     * @return captured image in base64 format
     * @throws CameraException could not access camera or some I/O error occured
     */
    String getCameraImageAsBase64() throws CameraException;

    /**
     * Works like {@link PiCameraUtil#getCameraImageAsBase64()} but returns {@link ImageModel} model instead of bytes
     * in base64 format.
     * @return image in {@link ImageModel} model
     * @throws CameraException could not access camera or some I/O error occured
     */
    default ImageModel getCameraImage() throws CameraException {
        return ImageModel.builder()
                .bytesAsBase64(this.getCameraImageAsBase64())
                .creationTime(LocalDateTime.now())
                .build();
    }
}
