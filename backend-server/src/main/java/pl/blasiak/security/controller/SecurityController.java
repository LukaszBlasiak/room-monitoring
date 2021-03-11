package pl.blasiak.security.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.blasiak.security.config.JwtConstants;
import pl.blasiak.security.model.JwtRequest;
import pl.blasiak.security.model.JwtResponse;
import pl.blasiak.security.service.JwtServiceImpl;

import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class SecurityController {

    private final AuthenticationManager authenticationManager;
    private final JwtServiceImpl jwtService;

    @PostMapping(value = "/logon")
    public ResponseEntity<?> generateAuthenticationToken(@RequestBody final JwtRequest authenticationRequest)
            throws Exception {

        final var authentication =
                this.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final var tokenData = JwtResponse.builder()
                .token(jwtService.createToken(authentication))
                .type(JwtConstants.TOKEN_TYPE)
                .build();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(tokenData);
    }

    private Authentication authenticate(final String username, final String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
