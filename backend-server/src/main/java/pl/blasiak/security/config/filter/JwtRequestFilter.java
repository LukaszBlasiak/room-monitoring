package pl.blasiak.security.config.filter;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.blasiak.security.config.JwtConstants;
import pl.blasiak.security.service.JwtServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtServiceImpl jwtService;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain chain) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            final var authorisationHeader = ObjectUtils.firstNonNull(
                    request.getHeader(HttpHeaders.AUTHORIZATION),
                    request.getParameter(HttpHeaders.AUTHORIZATION)); // due to lack of http header support by SOCK.JS
            if (StringUtils.startsWith(authorisationHeader, JwtConstants.BEARER)) {
                final var token = authorisationHeader.replaceFirst(JwtConstants.BEARER, StringUtils.EMPTY).trim();
                final var authentication = this.jwtService.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
