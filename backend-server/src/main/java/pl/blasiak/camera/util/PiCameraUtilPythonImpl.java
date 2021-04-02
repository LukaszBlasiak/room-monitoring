package pl.blasiak.camera.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.ResourceAccessException;
import pl.blasiak.application.config.ProfilesConfig;
import pl.blasiak.camera.exception.CameraException;
import pl.blasiak.camera.mapper.ImageModelMapper;
import pl.blasiak.common.util.ExternalApiUrl;
import pl.blasiak.common.util.ExternalApiUtilImpl;

import java.io.IOException;
import java.util.Collections;

/**
 * Pi camera preview util that uses Python REST library under the hood to access camera.
 * This approach is way faster comparing to Java implementation but
 * require additional REST call to Python script not related to Java Spring application.
 */
@Profile(ProfilesConfig.PROFILE_PROD)
@Component
public class PiCameraUtilPythonImpl extends PiCameraUtil {

    private final ExternalApiUtilImpl urlUtil;
    private static final Logger LOGGER = LogManager.getLogger();

    public PiCameraUtilPythonImpl(final ExternalApiUtilImpl urlUtil, final ImageModelMapper imageModelMapper) {
        super(imageModelMapper);
        this.urlUtil = urlUtil;
    }

    @Override
    public String getCameraImageAsBase64() {
        try {
            final byte[] imageAsBytes =
                    urlUtil.getRestCallResult(ExternalApiUrl.MEDIUM_ROOM_PREVIEW, HttpMethod.GET, Collections.emptyMap(), byte[].class);
            return Base64Utils.encodeToString(imageAsBytes);
        } catch (IOException | ResourceAccessException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CameraException(e.getMessage(), e);
        }
    }
}
