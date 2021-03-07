package pl.blasiak.security.controller;

import org.springframework.http.ResponseEntity;
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

    @PostMapping("/logon/keepAlive")
    public ResponseEntity<Void> keepAlive() {
        return ResponseEntity.ok().build();
    }
}
