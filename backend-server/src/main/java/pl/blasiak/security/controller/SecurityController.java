package pl.blasiak.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
@RequestMapping("/api")
public class SecurityController {

    @PostMapping("/logon")
    public ResponseEntity<Void> logon() {
        return ResponseEntity.ok().build();
    }
}
