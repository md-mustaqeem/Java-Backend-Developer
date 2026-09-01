package com.developer.service;

import com.developer.dto.AuthResponse;
import com.developer.dto.LoginRequest;
import com.developer.entity.User;
import com.developer.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @InjectMocks
    private AuthServiceImpl authService;


    @Test
    void login_shouldReturnAccessAndRefreshToken() {

        // Request
        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("admin123");

        // User
        User user = User.builder()
                .id(1)
                .username("admin")
                .password("encodedPassword")
                .role("ADMIN")
                .build();

        // Mock authentication
        when(authenticationManager.authenticate(any(
                UsernamePasswordAuthenticationToken.class)))
                .thenReturn(
                        new UsernamePasswordAuthenticationToken(
                                "admin",
                                null
                        )
                );

        // Mock database
        when(userRepository.findByUsername("admin"))
                .thenReturn(Optional.of(user));

        // Mock JWT
        when(jwtService.generateToken(user))
                .thenReturn("access-token");

        // Mock refresh token
        when(refreshTokenService.createRefreshToken(user))
                .thenReturn("refresh-token");

        // Call service
        AuthResponse response =
                authService.login(request);

        // Verify response
        assertNotNull(response);
        assertEquals("access-token", response.getAccessToken());
        assertEquals("refresh-token", response.getRefreshToken());
        assertEquals("Bearer", response.getTokenType());

        // Verify calls
        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        verify(userRepository, times(1))
                .findByUsername("admin");

        verify(jwtService, times(1))
                .generateToken(user);

        verify(refreshTokenService, times(1))
                .createRefreshToken(user);
    }


    @Test
    void login_shouldThrowExceptionWhenUserNotFound() {

        LoginRequest request = new LoginRequest();
        request.setUsername("unknown");
        request.setPassword("password");

        when(authenticationManager.authenticate(any(
                UsernamePasswordAuthenticationToken.class)))
                .thenReturn(
                        new UsernamePasswordAuthenticationToken(
                                "unknown",
                                null
                        )
                );

        when(userRepository.findByUsername("unknown"))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> authService.login(request)
        );

        verify(userRepository, times(1))
                .findByUsername("unknown");

        verify(jwtService, never())
                .generateToken(any());

        verify(refreshTokenService, never())
                .createRefreshToken(any());
    }
}