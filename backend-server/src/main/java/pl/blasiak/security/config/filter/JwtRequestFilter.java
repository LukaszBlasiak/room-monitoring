package pl.blasiak.security.config.filter;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.blasiak.security.converter.JwtMapper;
import pl.blasiak.security.service.JwtServiceImpl;
import pl.blasiak.security.util.CookieUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtServiceImpl jwtService;
    private final CookieUtil cookieUtil;
    private final JwtMapper jwtMapper;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain chain) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null && !request.getRequestURI().contains("/logon")) {
            final Cookie jwtCookie = this.cookieUtil.getJwtCookie(request);
            final var jwtResponse = this.jwtMapper.toJwtResponse(jwtCookie);
            if (jwtResponse != null && jwtResponse.getToken() != null) {
                final var token = jwtResponse.getToken();
                final var authentication = this.jwtService.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }

}
