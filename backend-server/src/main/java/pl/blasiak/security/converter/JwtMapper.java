package pl.blasiak.security.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.blasiak.security.model.JwtModel;

import javax.servlet.http.Cookie;

@Mapper(componentModel="spring")
public interface JwtMapper {

    @Mapping(target = "token", source = "value")
    @Mapping(target = "type", source = "name")
    JwtModel toJwtResponse(final Cookie jwtCookie);

    @Mapping(target = "token", source = "token")
    @Mapping(target = "type", source = "type")
    JwtModel toJwtResponse(final String type, final String token);

    @Mapping(target = "value", source = "token")
    @Mapping(target = "name", source = "type")
    Cookie toCookie(final JwtModel jwtModel);
}
