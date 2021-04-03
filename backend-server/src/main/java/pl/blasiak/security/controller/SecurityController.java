package pl.blasiak.security.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.blasiak.security.config.JwtConstants;
import pl.blasiak.security.mapper.JwtMapper;
import pl.blasiak.security.model.JwtModel;
import pl.blasiak.security.model.JwtRequest;
import pl.blasiak.security.service.AuthenticationService;
import pl.blasiak.security.service.JwtService;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Api(description = "Set of endpoints for Creating, Retrieving, and Deleting user's session. Token is returned in " +
        "request cookie with httpOnly=true flag.")
public class SecurityController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final JwtMapper jwtMapper;

    @PostMapping(value = "/logon")
    @ApiOperation("Returns a new JWT based on given credentials.")
    public ResponseEntity<JwtModel> generateAuthenticationToken(@RequestBody final JwtRequest authenticationRequest) {
        final var authentication = this.authenticationService
                .authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final var jwtModel = this.jwtMapper.toJwtResponse(JwtConstants.TOKEN_TYPE, jwtService.createToken(authentication));
        return new ResponseEntity<>(jwtModel, HttpStatus.OK);
    }
}
