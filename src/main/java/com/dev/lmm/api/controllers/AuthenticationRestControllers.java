package com.dev.lmm.api.controllers;

import com.dev.lmm.api.models.security.auth.AuthenticationRequest;
import com.dev.lmm.api.models.security.auth.AuthenticationResponse;
import com.dev.lmm.api.models.security.auth.AuthenticationService;
import com.dev.lmm.api.models.security.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dev-lmm/auth")
@RequiredArgsConstructor
public class AuthenticationRestControllers {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }


}
