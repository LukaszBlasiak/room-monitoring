package pl.blasiak.security.util;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.blasiak.security.config.JwtConstants;
import pl.blasiak.security.config.JwtProperties;
import pl.blasiak.security.model.JwtResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Component
@AllArgsConstructor
public class CookieUtilImpl implements CookieUtil {

    private final JwtProperties jwtProperties;


    @Override
    public void saveJwtCookie(final JwtResponse jwtResponse, final HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie(jwtResponse.getType(), jwtResponse.getToken());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(jwtProperties.getExpiration());
        cookie.setPath("/");
//        cookie.setSecure(true); // TODO: TBD
        httpServletResponse.addCookie(cookie);
    }

    @Override
    public Cookie getJwtCookie(final HttpServletRequest httpServletRequest) {
        final Cookie [] cookies = httpServletRequest.getCookies();
        return cookies == null ? null : Arrays.stream(cookies)
                .filter(cookie -> JwtConstants.TOKEN_TYPE.equals(cookie.getName()))
                .findFirst()
                .orElse(null);
    }
}
