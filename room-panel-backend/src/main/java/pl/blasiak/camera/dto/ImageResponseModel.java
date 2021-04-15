package pl.blasiak.camera.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class ImageResponseModel {

    private final String bytesAsBase64;
    private final LocalDateTime creationTime;
}
