package pl.blasiak.camera.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pl.blasiak.application.config.ProfilesConfig;
import pl.blasiak.camera.exception.CameraException;
import pl.blasiak.camera.mapper.ImageModelMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Profile(ProfilesConfig.PROFILE_LOCAL)
@Service
public class PiCameraUtilMocked extends PiCameraUtil {

    private static final String KITTEN_IMAGE_PATH = "classpath:mocked_preview_kitku.jpeg";
    private final String encodedMockedPreviewImage;

    public PiCameraUtilMocked(final ImageModelMapper imageModelMapper, @Value(KITTEN_IMAGE_PATH) final Resource resourceFile) {
        super(imageModelMapper);
        try (final InputStream fileInputStream = resourceFile.getInputStream()) {
            byte[] mockedPreviewImageBytes = fileInputStream.readAllBytes();
            this.encodedMockedPreviewImage =
                    new String(Base64.encodeBase64(mockedPreviewImageBytes), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new CameraException(e.getMessage(), e);
        }
    }

    @Override
    public String getCameraImageAsBase64() {
        return this.encodedMockedPreviewImage;
    }
}
