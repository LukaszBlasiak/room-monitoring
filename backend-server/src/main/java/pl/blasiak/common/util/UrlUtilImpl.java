package pl.blasiak.common.util;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

@Component
public class UrlUtilImpl implements UrlUtil {

    private static final RestTemplate restTemplate = new RestTemplate();

    @Override
    public <T> T getRestCallAsString(final URL url, final HttpMethod httpMethod, final Map<String, String> queryParams,
                                     final Class<T> targetClass) throws IOException {

        final var uriBuilder = UriComponentsBuilder.fromHttpUrl(url.toString());
        this.setQueryParams(uriBuilder, queryParams);
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
}
