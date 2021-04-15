package pl.blasiak.common.util;

import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public interface ExternalApiUtil {

    /**
     * Makes rest api call to given URL with specified http method and query params
     *
     * @param url         target URL
     * @param httpMethod  http method
     * @param queryParams query params. null considered valid
     * @param targetClass target model class that response will be mapped to
     * @param <T>         response model class
     * @return rest api response in given class format
     * @throws IOException incorrect URL or unexpected network error occurred
     */
    <T> T getRestCallResult(final ExternalApiUrl url, final HttpMethod httpMethod, Map<String, String> queryParams, Class<T> targetClass) throws IOException;

    /**
     * Makes rest api call to given URL with specified http method. Given URL should contain all query params if any exist.
     *
     * @param finalUrl   Final URL that contains all query params if any exist.
     * @param httpMethod http method
     * @return Result of REST api call as plain string.
     * @throws IOException incorrect URL or unexpected network error occurred
     */
    String getRestCallResultAsString(final URL finalUrl, final HttpMethod httpMethod) throws IOException;
}
