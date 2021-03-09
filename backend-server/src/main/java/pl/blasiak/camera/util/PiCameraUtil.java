package pl.blasiak.camera.util;

import pl.blasiak.application.exception.CameraException;
import pl.blasiak.camera.dto.ImageResponseModel;
import pl.blasiak.camera.mapper.ImageModelMapper;

public abstract class PiCameraUtil {

    private final ImageModelMapper imageModelMapper;

    protected PiCameraUtil(final ImageModelMapper imageModelMapper) {
        this.imageModelMapper = imageModelMapper;
    }

    /**
     * Works like {@link PiCameraUtil#getCameraImageAsBase64()} but returns {@link ImageResponseModel} model instead of bytes
     * in base64 format.
     * @return image in {@link ImageResponseModel} model
     * @throws CameraException could not access camera or some I/O error occured
     */
    public ImageResponseModel getCameraImage() throws CameraException {
        return this.imageModelMapper.toModel(this.getCameraImageAsBase64());
    }

    /**
     * Reads image from Raspberry Pi camera and conterts it into base64 format.
     * @return captured image in base64 format
     * @throws CameraException could not access camera or some I/O error occured
     */
    public abstract String getCameraImageAsBase64() throws CameraException;
}
