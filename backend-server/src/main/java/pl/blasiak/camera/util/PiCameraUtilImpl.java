package pl.blasiak.camera.util;

import com.hopding.jrpicam.RPiCamera;
import com.hopding.jrpicam.enums.Exposure;
import com.hopding.jrpicam.exceptions.FailedToRunRaspistillException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.blasiak.application.exception.CameraException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Profile("prod")
@Component
public class PiCameraUtilImpl implements PiCameraUtil {

    private final RPiCamera camera;
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;
    private static final String EXTENSION = "jpeg";

    public PiCameraUtilImpl() throws FailedToRunRaspistillException {
        this.camera = new RPiCamera();
        camera.setWidth(WIDTH);
        camera.setHeight(HEIGHT);
        camera.setExposure(Exposure.AUTO);
    }

    @Override
    public String getCameraImageAsBase64() {
        try (final ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            final BufferedImage image = camera.takeBufferedStill();
            ImageIO.write(image, EXTENSION, os);
            return Base64.getEncoder().encodeToString(os.toByteArray());
        } catch (IOException | InterruptedException e) {
            throw new CameraException(e.getMessage(), e);
        }
    }
}
