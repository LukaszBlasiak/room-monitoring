package pl.blasiak.camera.advice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.blasiak.application.dto.ErrorResponseModel;
import pl.blasiak.application.exception.CameraException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class CameraExceptionControllerAdvice {

    private static final Logger logger = LogManager.getLogger();

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ CameraException.class})
    public ResponseEntity<ErrorResponseModel> internalException(final CameraException e, HttpServletRequest request) {
        final var responseData = ErrorResponseModel.builder()
                .throwable(e)
                .path(request.getRequestURI())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        logger.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
    }

}
