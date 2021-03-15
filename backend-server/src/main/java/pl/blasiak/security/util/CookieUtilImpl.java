package pl.blasiak.security.util;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import pl.blasiak.security.config.JwtConstants;
import pl.blasiak.security.config.JwtProperties;
import pl.blasiak.security.model.JwtModel;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Component
@AllArgsConstructor
public class CookieUtilImpl implements CookieUtil {

    private final JwtProperties jwtProperties;


    @Override
    public void saveJwtCookie(final JwtModel jwtModel, final HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie(jwtModel.getType(), jwtModel.getToken());
        this.setCookieProperties(cookie, jwtProperties.getExpiration());
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

    @Override
    public void deleteJwtCookie(final HttpServletRequest httpServletRequest,
                                final HttpServletResponse httpServletResponse) {
        final Cookie jwtCookie = this.getJwtCookie(httpServletRequest);
        if (jwtCookie == null) {
            return;
        }
        jwtCookie.setValue(Strings.EMPTY);
        this.setCookieProperties(jwtCookie, 0);
        httpServletResponse.addCookie(jwtCookie);
    }

    private void setCookieProperties(final Cookie cookie, final int expirationTime) {
        if (cookie == null) {
            return;
        }
        cookie.setHttpOnly(true);
        cookie.setMaxAge(expirationTime);
        cookie.setPath("/");
        cookie.setSecure(jwtProperties.isSecureFlag());
    }
}
