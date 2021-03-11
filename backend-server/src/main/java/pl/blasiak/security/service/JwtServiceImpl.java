package pl.blasiak.security.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import pl.blasiak.security.config.JwtConstants;
import pl.blasiak.security.config.JwtProperties;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl {

    private final JwtProperties jwtProperties;
    private static final Function<LocalDateTime, Date> convertLocalDateTimeToDate = (localDateTime) -> Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    private static final Function<String, byte[]> encodeStringWithBase64 = (str) -> Base64.getEncoder().encode(str.getBytes());

    public JwtServiceImpl(final JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }


    public Authentication getAuthentication(@NonNull final String token) {
        final var claims = Jwts.parser()
                .setSigningKey(encodeStringWithBase64.apply(jwtProperties.getSecret()))
                .parseClaimsJws(token).getBody();

        final var authorities = Arrays.stream(
                claims.getOrDefault(JwtConstants.JWT_SCOPES, StringUtils.EMPTY).toString().split(","))
                .filter(StringUtils::isNotEmpty)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new UsernamePasswordAuthenticationToken(claims.getSubject(), StringUtils.EMPTY, authorities);
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

    public void validateToken(String token) {
        Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token);
    }
}
