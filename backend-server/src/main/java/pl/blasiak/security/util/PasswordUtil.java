package pl.blasiak.security.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {
    public void wipePassword(final char[] password) {
        final String randomString = RandomStringUtils.randomAlphabetic(password.length);
        for (int i = 0; i < password.length; i++) {
            password[i] = randomString.charAt(i);
        }
    }
}
