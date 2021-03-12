package pl.blasiak.security.controller;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.blasiak.application.dto.ErrorResponseModel;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ErrorResponseModel> badCredentialExceptionAdvice(BadCredentialsException e, HttpServletRequest request) {
        final var responseData = ErrorResponseModel.builder()
                .throwable(e)
                .path(request.getRequestURI())
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseData);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({DisabledException.class})
    public ResponseEntity<ErrorResponseModel> disabledUserExceptionAdvice(DisabledException e, HttpServletRequest request) {
        final var responseData = ErrorResponseModel.builder()
                .throwable(e)
                .path(request.getRequestURI())
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseData);
    }
}
