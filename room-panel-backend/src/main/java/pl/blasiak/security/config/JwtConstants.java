package pl.blasiak.security.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtConstants {
    public static final String BEARER = "Bearer";
    public static final String JWT_SCOPES = "scopes";
    public static final String TOKEN_TYPE = "JWT";
}
