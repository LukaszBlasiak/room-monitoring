package pl.blasiak.security.converter;

import org.mapstruct.Mapper;
import pl.blasiak.security.entity.UserEntity;
import pl.blasiak.security.model.SpringLoginDetails;

@Mapper(componentModel="spring")
public interface UserDetailsConverter {

    SpringLoginDetails map(UserEntity userEntity);

}
