package pl.blasiak.common.util;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.blasiak.application.config.PythonApiConfig;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

@Component
public class ExternalApiUtilImpl implements ExternalApiUtil {

    private final PythonApiConfig pythonApiConfig;
    private static final RestTemplate restTemplate = new RestTemplate();

    public ExternalApiUtilImpl(final PythonApiConfig pythonApiConfig) {
        this.pythonApiConfig = pythonApiConfig;
    }

    @Override
    public <T> T getRestCallResult(final ExternalApiUrl url, final HttpMethod httpMethod, final Map<String, String> queryParams,
                                   final Class<T> targetClass) throws IOException {

        final var uriBuilder = UriComponentsBuilder.fromHttpUrl(pythonApiConfig.getBaseUrl() + url.getUrl());
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
        uriBuilder.queryParam(PythonApiConfig.API_KEY_NAME, this.pythonApiConfig.getSecret());
    }

    @Override
    public String getRestCallResultAsString(final URL finalUrl, final HttpMethod httpMethod) throws IOException {
        final HttpsURLConnection connection = (HttpsURLConnection) finalUrl.openConnection();
        connection.setRequestMethod(httpMethod.name());
        connection.setConnectTimeout(3000);
        connection.connect();
        try (final var br = new BufferedReader(new InputStreamReader((connection.getInputStream())))) {
            final var sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            return sb.toString();
        } finally {
            connection.disconnect();
        }
    }
}
