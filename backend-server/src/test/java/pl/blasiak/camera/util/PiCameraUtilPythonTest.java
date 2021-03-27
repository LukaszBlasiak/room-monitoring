package pl.blasiak.camera.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.ResourceAccessException;
import pl.blasiak.application.config.ProfilesConfig;
import pl.blasiak.application.exception.CameraException;
import pl.blasiak.camera.mapper.ImageModelMapper;
import pl.blasiak.common.util.PythonApiUrl;
import pl.blasiak.common.util.PythonApiUtilImpl;

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
public class PiCameraUtilPythonTest {

    @Mock
    private PythonApiUtilImpl pythonApiUtil;

    @Autowired
    private ImageModelMapper imageModelMapper;

    private PiCameraUtil piCameraUtilPython;

    @BeforeEach
    void initMocks() {
        piCameraUtilPython = new PiCameraUtilPythonImpl(pythonApiUtil, imageModelMapper);
    }

    private byte[] getBase64ImageAsByteArray() {
        return "dGVzdCBpbWFnZSBieXRlcw==".getBytes(StandardCharsets.UTF_8);
    }

    @Test
    @DisplayName("Get camera image model from python API - should return image")
    public void getCameraImage_ShouldReturnImageModel() throws IOException {
        Mockito.when(this.pythonApiUtil.getRestCallResult(any(PythonApiUrl.class), eq(HttpMethod.GET), any(Map.class), any(Class.class)))
                .thenReturn(this.getBase64ImageAsByteArray());

        final var imageModel = this.piCameraUtilPython.getCameraImage();
        assertNotNull(imageModel);
        assertEquals(imageModel.getCreationTime().truncatedTo(ChronoUnit.MINUTES), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        assertDoesNotThrow(() -> Base64.getDecoder().decode(imageModel.getBytesAsBase64()));
    }

    @Test
    @DisplayName("Get camera image model from python API - connection timed out - should throw camera exception")
    public void getCameraImage_ConnectionTimedOut_ShouldThrowCameraException() throws IOException {
        Mockito.when(this.pythonApiUtil.getRestCallResult(any(PythonApiUrl.class), eq(HttpMethod.GET), any(Map.class), any(Class.class)))
                .thenThrow(new ResourceAccessException("Connect timed out"));

        assertThrows(CameraException.class, () -> this.piCameraUtilPython.getCameraImage());
    }

    @Test
    @DisplayName("Get camera image model from python API - incorrect URL - should throw camera exception")
    public void getCameraImage_IncorrectUrl_ShouldThrowCameraException() throws IOException {
        Mockito.when(this.pythonApiUtil.getRestCallResult(any(PythonApiUrl.class), eq(HttpMethod.GET), any(Map.class), any(Class.class)))
                .thenThrow(new IOException("Incorrect URL"));

        assertThrows(CameraException.class, () -> this.piCameraUtilPython.getCameraImage());
    }

    @Test
    @DisplayName("Get camera image in base64 format from python API - should return image")
    public void getCameraImageAsBase64_ShouldReturnImageAsBase64() throws IOException {
        Mockito.when(this.pythonApiUtil.getRestCallResult(any(PythonApiUrl.class), eq(HttpMethod.GET), any(Map.class), any(Class.class)))
                .thenReturn(this.getBase64ImageAsByteArray());

        final var imageAsBase64 = this.piCameraUtilPython.getCameraImageAsBase64();
        assertDoesNotThrow(() -> Base64.getDecoder().decode(imageAsBase64));
    }

    @Test
    @DisplayName("Get camera image in base64 format from python API - connection timed out - should throw camera exception")
    public void getCameraImageAsBase64_ConnectionTimedOut_ShouldThrowCameraException() throws IOException {
        Mockito.when(this.pythonApiUtil.getRestCallResult(any(PythonApiUrl.class), eq(HttpMethod.GET), any(Map.class), any(Class.class)))
                .thenThrow(new ResourceAccessException("Connect timed out"));

        assertThrows(CameraException.class, () -> this.piCameraUtilPython.getCameraImageAsBase64());
    }

    @Test
    @DisplayName("Get camera image in base64 format from python API - incorrect URL - should throw camera exception")
    public void getCameraImageAsBase64_IncorrectUrl_ShouldThrowCameraException() throws IOException {
        Mockito.when(this.pythonApiUtil.getRestCallResult(any(PythonApiUrl.class), eq(HttpMethod.GET), any(Map.class), any(Class.class)))
                .thenThrow(new IOException("Incorrect URL"));

        assertThrows(CameraException.class, () -> this.piCameraUtilPython.getCameraImageAsBase64());
    }
}
