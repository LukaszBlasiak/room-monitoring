package pl.blasiak.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt.token")
public class JwtProperties {
    private String secret;
    private String algorithm;
    private int expiration;
}
