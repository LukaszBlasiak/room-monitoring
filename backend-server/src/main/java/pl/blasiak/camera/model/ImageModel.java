package pl.blasiak.camera.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
public class ImageModel {

    private final String bytesAsBase64;
    private final LocalDateTime creationTime;
}
