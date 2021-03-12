package pl.blasiak.application.advice;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.blasiak.application.dto.ErrorResponseModel;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order()
public class InternalExceptionControllerAdvice {

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ Exception.class, RuntimeException.class})
    public ResponseEntity<ErrorResponseModel> internalException(final Exception e, final HttpServletRequest request) {
        final var responseData = ErrorResponseModel.builder()
                .throwable(e)
                .path(request.getRequestURI())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
    }

}
