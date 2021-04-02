package pl.blasiak.application.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "python.api")
@Getter
@Setter
public class PythonApiConfig {

    private String url;

}
