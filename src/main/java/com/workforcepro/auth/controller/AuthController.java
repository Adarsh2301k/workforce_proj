package com.workforcepro.auth.controller;

import com.workforcepro.auth.dto.AuthResponse;
import com.workforcepro.auth.dto.LoginRequest;
import com.workforcepro.auth.dto.RegisterRequest;
import com.workforcepro.auth.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}