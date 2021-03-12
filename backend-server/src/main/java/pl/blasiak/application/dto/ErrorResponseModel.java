package pl.blasiak.application.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@JsonPropertyOrder(value = {"timestamp", "status", "error", "message", "path", "throwable"})
public class ErrorResponseModel {
    @Getter
    private final LocalDateTime timestamp;
    private final HttpStatus httpStatus;
    private final Throwable throwable;
    @Getter
    private final String path;

    @Builder
    public ErrorResponseModel(HttpStatus httpStatus, Throwable throwable, boolean enableStackTrace, String path) {
        this.timestamp = LocalDateTime.now();
        this.httpStatus = httpStatus;
        this.throwable = throwable;
        this.path = path;
    }

    public Integer getStatus() {
        if (httpStatus != null) {
            return httpStatus.value();
        }
        return null;
    }

    public String getError() {
        if (httpStatus != null) {
            return httpStatus.getReasonPhrase();
        }
        return null;
    }

    public String getMessage() {
        if (throwable != null) {
            return throwable.getMessage();
        }
        return null;
    }

    public String getThrowable() {
        if (throwable != null) {
            return throwable.getClass().getName();
        }
        return null;
    }
}
