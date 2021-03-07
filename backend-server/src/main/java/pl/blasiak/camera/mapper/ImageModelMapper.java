package pl.blasiak.camera.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.blasiak.camera.dto.ImageModel;

@Mapper(componentModel="spring")
public interface ImageModelMapper {

    @Mapping(target = "creationTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "bytesAsBase64", source = "bytesAsBase64")
    ImageModel toModel(final String bytesAsBase64);
}
