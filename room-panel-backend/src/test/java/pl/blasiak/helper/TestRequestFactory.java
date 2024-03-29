package pl.blasiak.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.blasiak.security.config.JwtConstants;
import pl.blasiak.security.service.JwtService;

@Component
public class TestRequestFactory {

    public final static String USERNAME = "admin";
    public final static String PASSWORD = "password";

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    public MockHttpServletRequestBuilder get(final String url) {
        final var authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(USERNAME, PASSWORD));
        final var token = jwtService.createToken(authentication);
        return MockMvcRequestBuilders.get(url)
                .header(HttpHeaders.AUTHORIZATION, String.format("%s %s", JwtConstants.BEARER, token));
    }

    public String getAuthToken() {
        final var authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(USERNAME, PASSWORD));
        return jwtService.createToken(authentication);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
