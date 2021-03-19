package pl.blasiak.application.advice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.blasiak.application.dto.ErrorResponseModel;
import pl.blasiak.common.dto.ErrorCode;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order()
public class InternalExceptionControllerAdvice {

    private static final Logger logger = LogManager.getLogger();

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ Exception.class, RuntimeException.class})
    public ResponseEntity<ErrorResponseModel> internalException(final Exception e, final HttpServletRequest request) {
        final var responseData = ErrorResponseModel.builder()
                .path(request.getRequestURI())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorCode(ErrorCode.INTERNAL_01.name())
                .message(ErrorCode.INTERNAL_01.getMessage())
                .build();
        logger.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
    }

}
