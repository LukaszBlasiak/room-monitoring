package pl.blasiak.security.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.blasiak.security.config.JwtConstants;
import pl.blasiak.security.converter.JwtMapper;
import pl.blasiak.security.model.JwtRequest;
import pl.blasiak.security.service.JwtServiceImpl;
import pl.blasiak.security.util.CookieUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Api(description = "Set of endpoints for Creating, Retrieving, and Deleting user's session. Token is returned in " +
                   "request cookie with httpOnly=true flag.")
public class SecurityController {

    private final AuthenticationManager authenticationManager;
    private final JwtServiceImpl jwtService;
    private final JwtMapper jwtMapper;
    private final CookieUtil cookieUtil;

    @PostMapping(value = "/logon")
    @ApiOperation("Returns a new JWT based on given credentials.")
    public ResponseEntity<Void> generateAuthenticationToken(@RequestBody final JwtRequest authenticationRequest,
                                                            final HttpServletResponse httpServletResponse) {
        final var authentication =
                this.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final var jwtResponse = this.jwtMapper.toJwtResponse(JwtConstants.TOKEN_TYPE, jwtService.createToken(authentication));
        this.cookieUtil.saveJwtCookie(jwtResponse, httpServletResponse);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    private Authentication authenticate(final String username, final String password) {
        if (username == null || password == null) {
            throw new BadCredentialsException("Username or password must not be null");
        }
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @PostMapping("/logout")
    @ApiOperation("Performs logout operation by deleting JWT given in cookie with httpOnly=true flag.")
    public ResponseEntity<Void> logout(final HttpServletRequest httpServletRequest,
                                       final HttpServletResponse httpServletResponse) {
        this.cookieUtil.deleteJwtCookie(httpServletRequest, httpServletResponse);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/validate")
    @ApiOperation("Validates whether given JWT token is valid - otherwise returns 401 code. Token must be provided in" +
            "request cookie with httpOnly=true flag.")
    public ResponseEntity<Void> validateJwt() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
