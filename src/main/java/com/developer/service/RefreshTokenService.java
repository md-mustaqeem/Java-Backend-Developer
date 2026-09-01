package com.developer.service;

import com.developer.dto.AuthResponse;
import com.developer.entity.User;

public interface RefreshTokenService {

    String createRefreshToken(User user);

    AuthResponse refreshAccessToken(String refreshToken);
}