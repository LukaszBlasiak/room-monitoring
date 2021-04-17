package pl.blasiak.camera.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.blasiak.application.config.ProfilesConfig;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles(ProfilesConfig.PROFILE_LOCAL)
class PiCameraMapperTest {

    @Autowired
    private ImageModelMapper imageModelMapper;

    @Test
    @DisplayName("To model - should return converted image")
    void getCameraImage_ShouldReturnImageModel() {
        final var responseImageBytes = "bytes_as_base64";
        final var imageModel = this.imageModelMapper.toModel(responseImageBytes);
        assertNotNull(imageModel);
        assertEquals(imageModel.getCreationTime().truncatedTo(ChronoUnit.MINUTES), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        assertEquals(responseImageBytes, imageModel.getBytesAsBase64());
    }
}
