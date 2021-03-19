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
import pl.blasiak.common.dto.ErrorCode;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ErrorResponseModel> badCredentialExceptionAdvice(BadCredentialsException e, HttpServletRequest request) {
        final var responseData = ErrorResponseModel.builder()
                .path(request.getRequestURI())
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .errorCode(ErrorCode.AUTH_01.name())
                .message(ErrorCode.AUTH_01.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseData);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({DisabledException.class})
    public ResponseEntity<ErrorResponseModel> disabledUserExceptionAdvice(DisabledException e, HttpServletRequest request) {
        final var responseData = ErrorResponseModel.builder()
                .path(request.getRequestURI())
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .errorCode(ErrorCode.AUTH_02.name())
                .message(ErrorCode.AUTH_02.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseData);
    }
}
