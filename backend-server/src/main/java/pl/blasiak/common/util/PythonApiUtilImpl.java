package pl.blasiak.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.blasiak.camera.exception.CameraException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Component
public class PythonApiUtilImpl implements PythonApiUtil {

    protected static final String CAMERA_API_BASE_URL = "http://192.168.0.115:5000";
    protected static final String CAMERA_API_SECRET = "r[8ikCroO!z3B$S^xkszT";
    protected static final String CAMERA_API_KEY = "key";
    private final URL CAMERA_API_URL;
    private static final Logger LOGGER = LogManager.getLogger();
    private static final RestTemplate restTemplate = new RestTemplate();

    public PythonApiUtilImpl() {
        try {
            CAMERA_API_URL = new URL(CAMERA_API_BASE_URL);
        } catch (MalformedURLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CameraException(e.getMessage(), e);
        }
    }

    @Override
    public <T> T getRestCallResult(final PythonApiUrl url, final HttpMethod httpMethod, final Map<String, String> queryParams,
                                   final Class<T> targetClass) throws IOException {

        final var uriBuilder = UriComponentsBuilder.fromHttpUrl(CAMERA_API_URL.toString() + url.getUrl());
        this.setQueryParams(uriBuilder, queryParams);
        this.setApiKey(uriBuilder);
        final ResponseEntity<T> response =
                restTemplate.exchange(uriBuilder.build(false).toUriString(), httpMethod, null, targetClass);
        return response.getBody();
    }

    private void setQueryParams(final UriComponentsBuilder uriBuilder, final Map<String, String> queryParams) {
        if (queryParams == null) {
            return;
        }
        for (final var param : queryParams.entrySet()) {
            uriBuilder.queryParam(param.getKey(), param.getValue());
        }
    }

    private void setApiKey(final UriComponentsBuilder uriBuilder) {
        uriBuilder.queryParam(CAMERA_API_KEY, CAMERA_API_SECRET);
    }
}
