package pl.blasiak.camera.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import pl.blasiak.application.exception.CameraException;
import pl.blasiak.camera.mapper.ImageModelMapper;
import pl.blasiak.common.util.UrlUtilImpl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Pi camera preview util that uses Python REST library under the hood to access camera.
 * This approach is way faster comparing to Java implementation but
 * require additional REST call to Python script not related to Java Spring application.
 */
@Profile("local")
@Component
public class PiCameraUtilPythonImpl extends PiCameraUtil {

    private final UrlUtilImpl urlUtil;
    private static final Logger LOGGER = LogManager.getLogger();
    private final URL CAMERA_API_URL;
    private static final String CAMERA_API_BASE_URL = "http://192.168.0.115:5000/cameraPreview";
    private static final String CAMERA_API_SECRET = "r[8ikCroO!z3B$S^xkszT";
    private static final String CAMERA_API_KEY = "key";
    private static final Map<String, String> CAMERA_API_PARAMS = Map.of(CAMERA_API_KEY, CAMERA_API_SECRET);

    public PiCameraUtilPythonImpl(final UrlUtilImpl urlUtil, final ImageModelMapper imageModelMapper) {
        super(imageModelMapper);
        this.urlUtil = urlUtil;
        try {
            CAMERA_API_URL = new URL(CAMERA_API_BASE_URL);
        } catch (MalformedURLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CameraException(e.getMessage(), e);
        }
    }

    @Override
    public String getCameraImageAsBase64() {
        try {
            final byte[] imageAsBytes =
                    urlUtil.getRestCallAsString(CAMERA_API_URL, HttpMethod.GET, CAMERA_API_PARAMS, byte[].class);
            return Base64Utils.encodeToString(imageAsBytes);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CameraException(e.getMessage(), e);
        }
    }
}
