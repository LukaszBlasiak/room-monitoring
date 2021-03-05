package pl.blasiak.application.advice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.blasiak.application.exception.CameraException;

@ControllerAdvice
public class AdviceController {

    private static final Logger logger = LogManager.getLogger();

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ Exception.class, RuntimeException.class, CameraException.class})
    public void internalException(Exception e) {
        logger.error(e.getMessage(), e);
    }
}
