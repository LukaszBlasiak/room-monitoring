package pl.blasiak.application.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@JsonPropertyOrder(value = {"timestamp", "httpStatus", "errorCode", "message", "path"})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseModel {
    private LocalDateTime timestamp;
    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
    private String path;

    @Builder
    public ErrorResponseModel(final HttpStatus httpStatus, final String message, final String errorCode, final String path) {
        this.timestamp = LocalDateTime.now();
        this.httpStatus = httpStatus;
        this.path = path;
        this.message = message;
        this.errorCode = errorCode;
    }
}
