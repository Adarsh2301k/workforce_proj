package com.workforcepro.auth.service;

import com.workforcepro.auth.dto.AuthResponse;
import com.workforcepro.auth.dto.LoginRequest;
import com.workforcepro.auth.dto.RegisterRequest;

public interface AuthService {

    String register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}