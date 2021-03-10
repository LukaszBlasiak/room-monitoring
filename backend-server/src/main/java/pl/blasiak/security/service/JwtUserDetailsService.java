package pl.blasiak.security.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.blasiak.security.config.JwtConstants;
import pl.blasiak.security.config.JwtProperties;
import pl.blasiak.security.converter.UserDetailsConverter;
import pl.blasiak.security.entity.UserEntity;
import pl.blasiak.security.repository.UserRepository;
import pl.blasiak.security.util.PasswordUtil;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final PasswordUtil passwordUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsConverter userDetailsConverter;
    private final JwtProperties jwtProperties;
    private static final Function<LocalDateTime, Date> convertLocalDateTimeToDate = (localDateTime) -> Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    private static final Function<String, byte[]> encodeStringWithBase64 = (str) -> Base64.getEncoder().encode(str.getBytes());

    private static final char[] password = {'p', 'a', 's', 's', 'w', 'o', 'r', 'd'};

    public JwtUserDetailsService(final PasswordUtil passwordUtil, final UserRepository userRepository,
                                 @Lazy final PasswordEncoder passwordEncoder, final JwtProperties jwtProperties,
                                 final UserDetailsConverter userDetailsConverter) {
        this.passwordUtil = passwordUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsConverter = userDetailsConverter;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final UserEntity foundUser = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return this.userDetailsConverter.map(foundUser);
    }

    public String createToken(@NonNull final Authentication authentication) {
        final var now = LocalDateTime.now();
        final var expirationTime = now.plusSeconds(jwtProperties.getExpiration());
        final var authorities = Collections.emptyList();

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(JwtConstants.JWT_SCOPES, authorities)
                .signWith(SignatureAlgorithm.forName(jwtProperties.getAlgorithm()), encodeStringWithBase64.apply(jwtProperties.getSecret()))
                .setIssuedAt(convertLocalDateTimeToDate.apply(now))
                .setExpiration(convertLocalDateTimeToDate.apply(expirationTime))
                .compact();
    }

    public void validateToken(@NonNull final String token) {
        Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token);
    }

    @PostConstruct
    private void initializeUsers() {
        final UserEntity userToSave =
                new UserEntity(null, "lukasz", true, passwordEncoder.encode(String.valueOf(password)));
        this.userRepository.save(userToSave);
        this.passwordUtil.wipePassword(password);
    }
}
