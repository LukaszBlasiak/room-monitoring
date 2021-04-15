package pl.blasiak.security.service;

import lombok.NonNull;
import org.springframework.security.core.Authentication;

public interface JwtService {

    Authentication getAuthentication(@NonNull final String token);

    String createToken(@NonNull final Authentication authentication);
}
