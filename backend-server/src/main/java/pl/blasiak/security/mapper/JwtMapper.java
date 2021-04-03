package pl.blasiak.security.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.blasiak.security.model.JwtModel;

@Mapper(componentModel="spring")
public interface JwtMapper {


    @Mapping(target = "token", source = "token")
    @Mapping(target = "type", source = "type")
    JwtModel toJwtResponse(final String type, final String token);

}
