package pl.blasiak.camera.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.blasiak.application.config.ProfilesConfig;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(ProfilesConfig.PROFILE_LOCAL)
public class PiCameraUtilTest {

    @Autowired
    private PiCameraUtil piCameraUtilMocked;

    @Test
    @DisplayName("Get camera image model from mocked API - should return image")
    public void getCameraImage_ShouldReturnImageModel() {
        final var imageModel = this.piCameraUtilMocked.getCameraImage();
        assertNotNull(imageModel);
        assertEquals(imageModel.getCreationTime().truncatedTo(ChronoUnit.MINUTES), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        assertDoesNotThrow(() -> Base64.getDecoder().decode(imageModel.getBytesAsBase64()));
    }

    @Test
    @DisplayName("Get camera image in base64 format from mocked API - should return image")
    public void getCameraImageAsBase64_ShouldReturnImageAsBase64() {
        final var imageAsBase64 = this.piCameraUtilMocked.getCameraImageAsBase64();
        assertDoesNotThrow(() -> Base64.getDecoder().decode(imageAsBase64));
    }
}
