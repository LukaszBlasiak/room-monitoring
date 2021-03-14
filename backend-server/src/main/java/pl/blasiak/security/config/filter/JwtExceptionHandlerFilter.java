package pl.blasiak.security.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.blasiak.application.dto.ErrorResponseModel;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implementation of {@link OncePerRequestFilter} used to catch {@link JwtException} and response with
 * HTTP 401 Unauthorized code
 */
@Component
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper mapper;

    public JwtExceptionHandlerFilter(@Autowired ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest httpServletRequest,
                                    final HttpServletResponse httpServletResponse, final FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (JwtException e) {
            var errorResponse = ErrorResponseModel.builder()
                    .httpStatus(HttpStatus.UNAUTHORIZED)
                    .path(httpServletRequest.getRequestURI())
                    .throwable(e)
                    .build();
            httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.setStatus(errorResponse.getStatus());
            httpServletResponse.getWriter().write(mapper.writeValueAsString(mapper.writeValueAsString(errorResponse)));
        }
    }
}
