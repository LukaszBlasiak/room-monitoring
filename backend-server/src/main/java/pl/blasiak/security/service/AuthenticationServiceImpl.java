package pl.blasiak.security.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.blasiak.security.entity.UserEntity;
import pl.blasiak.security.repository.UserRepository;

@Component
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Override
    public UserEntity getCurrentProfile() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new SecurityException("Current profile not found in database"));
    }

    @Override
    public Authentication authenticate(final String username, final String password) {
        if (username == null || password == null) {
            throw new BadCredentialsException("Username or password must not be null");
        }
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
