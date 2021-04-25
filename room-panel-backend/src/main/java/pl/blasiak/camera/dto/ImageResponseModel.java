package pl.blasiak.camera.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class ImageResponseModel {

    @Schema(description = "Image captured by RPi Camera in Base64 format.")
    private final String bytesAsBase64;
    @Schema(description = "Image creation time.")
    private final LocalDateTime creationTime;
}
