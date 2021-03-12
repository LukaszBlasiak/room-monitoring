package pl.blasiak.security.converter;

import org.mapstruct.Mapper;
import pl.blasiak.security.entity.UserEntity;
import pl.blasiak.security.model.SpringLoginDetails;

@Mapper(componentModel="spring")
public interface UserDetailsMapper {

    SpringLoginDetails map(UserEntity userEntity);

}
