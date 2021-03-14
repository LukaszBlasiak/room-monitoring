package pl.blasiak.security.util;

import pl.blasiak.security.model.JwtModel;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CookieUtil {

    void saveJwtCookie(final JwtModel jwtModel, final HttpServletResponse httpServletResponse);

    Cookie getJwtCookie(final HttpServletRequest httpServletRequest);
}
