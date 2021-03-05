package pl.blasiak.camera.util;

import com.hopding.jrpicam.RPiCamera;
import com.hopding.jrpicam.enums.Exposure;
import com.hopding.jrpicam.exceptions.FailedToRunRaspistillException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger();

    private final RPiCamera camera;
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final String EXTENSION = "jpeg";

    public PiCameraUtilImpl() throws FailedToRunRaspistillException {
        try {
            this.camera = new RPiCamera();
            camera.setWidth(WIDTH);
            camera.setHeight(HEIGHT);
            camera.setExposure(Exposure.AUTO);
        } catch (FailedToRunRaspistillException e) {
            logger.error(e.getMessage(), e);
            throw new CameraException(e.getMessage(), e);
        }
    }

    @Override
    public String getCameraImageAsBase64() {
        try (final ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            long start = System.nanoTime();
            final BufferedImage image = camera.takeBufferedStill();
            long elapsedTime = System.nanoTime() - start;
            logger.info("wykonanie zdjecia: " + elapsedTime);
            start = System.nanoTime();
            ImageIO.write(image, EXTENSION, os);
            elapsedTime = System.nanoTime() - start;
            logger.info("Zapis do bufora: " + elapsedTime);
            start = System.nanoTime();
            final String b = Base64.getEncoder().encodeToString(os.toByteArray());
            elapsedTime = System.nanoTime() - start;
            logger.info("Base64: " + elapsedTime);
            return b;
        } catch (IOException | InterruptedException e) {
            throw new CameraException(e.getMessage(), e);
        }
    }
}
