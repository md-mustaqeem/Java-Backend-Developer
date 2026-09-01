package com.developer.service;

import com.developer.dto.AuthResponse;
import com.developer.dto.LoginRequest;

public interface AuthService {

    AuthResponse login(LoginRequest request);
}