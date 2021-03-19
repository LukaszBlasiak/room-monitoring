package pl.blasiak.camera.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import pl.blasiak.application.exception.CameraException;
import pl.blasiak.camera.mapper.ImageModelMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Profile("local")
@Component
public class PiCameraUtilMocked extends PiCameraUtil {

    public PiCameraUtilMocked(final ImageModelMapper imageModelMapper) {
        super(imageModelMapper);
    }

    @Override
    public String getCameraImageAsBase64() {
        try {
            final var imgFile = ResourceUtils.getFile("classpath:mocked_preview_kitku.jpeg");
            try(final FileInputStream fileInputStreamReader = new FileInputStream(imgFile)) {
                byte[] bytes = new byte[(int)imgFile.length()];
                fileInputStreamReader.read(bytes);
                return new String(Base64.encodeBase64(bytes), StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new CameraException(e.getMessage(), e);
        }
    }
}
