package com.nimshub.biobeacon.auth;

import com.nimshub.biobeacon.user.Role;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService authService;
    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegisterRequest request) {
        request.setRole(Role.ADMIN);
        logger.info("Registering user...");
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        logger.info("Authenticating user...");
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
