package pl.blasiak.camera.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.ResourceAccessException;
import pl.blasiak.application.config.ProfilesConfig;
import pl.blasiak.camera.exception.CameraException;
import pl.blasiak.common.util.ExternalApiUrl;
import pl.blasiak.common.util.ExternalApiUtilImpl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
@ActiveProfiles(ProfilesConfig.PROFILE_PROD)
class PiCameraUtilPythonTest {

    @MockBean
    private ExternalApiUtilImpl pythonApiUtil;

    @Autowired
    private PiCameraUtil piCameraUtilPython;


    private byte[] getBase64ImageAsByteArray() {
        return "dGVzdCBpbWFnZSBieXRlcw==".getBytes(StandardCharsets.UTF_8);
    }

    @Test
    @DisplayName("Get camera image model from python API - should return image")
    void getCameraImage_ShouldReturnImageModel() throws IOException {
        Mockito.when(this.pythonApiUtil.getRestCallResult(any(ExternalApiUrl.class), eq(HttpMethod.GET), any(Map.class), any(Class.class)))
                .thenReturn(this.getBase64ImageAsByteArray());

        final var imageModel = this.piCameraUtilPython.getCameraImage();
        assertNotNull(imageModel);
        assertEquals(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), imageModel.getCreationTime().truncatedTo(ChronoUnit.MINUTES));
        assertDoesNotThrow(() -> Base64.getDecoder().decode(imageModel.getBytesAsBase64()));
    }

    @Test
    @DisplayName("Get camera image model from python API - connection timed out - should throw camera exception")
    void getCameraImage_ConnectionTimedOut_ShouldThrowCameraException() throws IOException {
        Mockito.when(this.pythonApiUtil.getRestCallResult(any(ExternalApiUrl.class), eq(HttpMethod.GET), any(Map.class), any(Class.class)))
                .thenThrow(new ResourceAccessException("Connect timed out"));

        assertThrows(CameraException.class, () -> this.piCameraUtilPython.getCameraImage());
    }

    @Test
    @DisplayName("Get camera image model from python API - incorrect URL - should throw camera exception")
    void getCameraImage_IncorrectUrl_ShouldThrowCameraException() throws IOException {
        Mockito.when(this.pythonApiUtil.getRestCallResult(any(ExternalApiUrl.class), eq(HttpMethod.GET), any(Map.class), any(Class.class)))
                .thenThrow(new IOException("Incorrect URL"));

        assertThrows(CameraException.class, () -> this.piCameraUtilPython.getCameraImage());
    }

    @Test
    @DisplayName("Get camera image in base64 format from python API - should return image")
    void getCameraImageAsBase64_ShouldReturnImageAsBase64() throws IOException {
        Mockito.when(this.pythonApiUtil.getRestCallResult(any(ExternalApiUrl.class), eq(HttpMethod.GET), any(Map.class), any(Class.class)))
                .thenReturn(this.getBase64ImageAsByteArray());

        final var imageAsBase64 = this.piCameraUtilPython.getCameraImageAsBase64();
        assertDoesNotThrow(() -> Base64.getDecoder().decode(imageAsBase64));
    }

    @Test
    @DisplayName("Get camera image in base64 format from python API - connection timed out - should throw camera exception")
    void getCameraImageAsBase64_ConnectionTimedOut_ShouldThrowCameraException() throws IOException {
        Mockito.when(this.pythonApiUtil.getRestCallResult(any(ExternalApiUrl.class), eq(HttpMethod.GET), any(Map.class), any(Class.class)))
                .thenThrow(new ResourceAccessException("Connect timed out"));

        assertThrows(CameraException.class, () -> this.piCameraUtilPython.getCameraImageAsBase64());
    }

    @Test
    @DisplayName("Get camera image in base64 format from python API - incorrect URL - should throw camera exception")
    void getCameraImageAsBase64_IncorrectUrl_ShouldThrowCameraException() throws IOException {
        Mockito.when(this.pythonApiUtil.getRestCallResult(any(ExternalApiUrl.class), eq(HttpMethod.GET), any(Map.class), any(Class.class)))
                .thenThrow(new IOException("Incorrect URL"));

        assertThrows(CameraException.class, () -> this.piCameraUtilPython.getCameraImageAsBase64());
    }
}
