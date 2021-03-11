package pl.blasiak.security.util;

import org.springframework.stereotype.Component;

@Component
public class CookieUtilImpl implements CookieUtil {

//    public HttpCookie createJwtCookie(String token, Long duration) {
//        String encryptedToken = SecurityCipher.encrypt(token);
//        return ResponseCookie.from(accessTokenCookieName, encryptedToken)
//                .maxAge(duration)
//                .httpOnly(true)
//                .path("/")
//                .build();
//    }
}
