package pl.blasiak;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class BackendServerApplication extends SpringBootServletInitializer {

    private static final Logger consoleLogger = LogManager.getLogger();

    public static void main(String [] args) {
        SpringApplication.run(BackendServerApplication.class);
        consoleLogger.log(Level.INFO, "Swagger access URL: {}", "http://localhost:8080/swagger-ui.html");
    }
}
