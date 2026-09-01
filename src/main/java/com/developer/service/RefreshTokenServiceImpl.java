package com.developer.service;

import com.developer.dto.AuthResponse;
import com.developer.entity.RefreshToken;
import com.developer.entity.User;
import com.developer.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl
        implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtService jwtService;


    @Override
    public String createRefreshToken(User user) {

        String token = UUID.randomUUID().toString();

        RefreshToken refreshToken = RefreshToken.builder()
                        .token(token)
                        .user(user)
                        .expiryDate(LocalDateTime.now().plusDays(7))
                        .revoked(false)
                        .build();
        refreshTokenRepository.save(refreshToken);
        return token;
    }


    @Override
    public AuthResponse refreshAccessToken(String token) {

        RefreshToken oldToken =
                refreshTokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (oldToken.isRevoked()) {
            throw new RuntimeException("Refresh token has been revoked");
        }

        if (oldToken.getExpiryDate().isBefore(LocalDateTime.now())) {

            throw new RuntimeException("Refresh token has expired");
        }
        User user = oldToken.getUser();

        oldToken.setRevoked(true);
        refreshTokenRepository.save(oldToken);

        String newAccessToken = jwtService.generateToken(user);
        String newRefreshToken = createRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .tokenType("Bearer")
                .build();
    }
}