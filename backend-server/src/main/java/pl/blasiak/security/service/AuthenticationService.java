package pl.blasiak.security.service;

import org.springframework.security.core.Authentication;
import pl.blasiak.security.entity.UserEntity;

public interface AuthenticationService {

    UserEntity getCurrentProfile();

    Authentication authenticate(final String username, final String password);
}
