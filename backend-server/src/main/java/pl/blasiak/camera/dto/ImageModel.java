package pl.blasiak.camera.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class ImageModel {

    private final String bytesAsBase64;
    private final LocalDateTime creationTime;
}
