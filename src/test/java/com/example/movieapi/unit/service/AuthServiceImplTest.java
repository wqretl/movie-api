package com.example.movieapi.unit.service;

import com.example.movieapi.dto.RegisterRequest;
import com.example.movieapi.dto.TokenPair;
import com.example.movieapi.entity.Role;
import com.example.movieapi.entity.User;
import com.example.movieapi.exception.BusinessException;
import com.example.movieapi.exception.DuplicateResourceException;
import com.example.movieapi.repository.RefreshTokenRepository;
import com.example.movieapi.repository.RoleRepository;
import com.example.movieapi.repository.UserRepository;
import com.example.movieapi.security.jwt.JwtTokenProvider;
import com.example.movieapi.service.impl.AuthServiceImpl;
import com.example.movieapi.utils.TestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    private RegisterRequest validRequest;
    private User testUser;
    private Role userRole;

    @BeforeEach
    void setUp() {
        validRequest = TestDataFactory.createValidRegisterRequest();
        testUser = TestDataFactory.createTestUser();
        userRole = TestDataFactory.createUserRole();
    }

    @Test
    void userRegister_Success_ShouldReturnAuthResponse() {
        // GIVEN
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(userRole));
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(jwtTokenProvider.generateAndSaveTokens(any(User.class)))
                .thenReturn(new TokenPair("access_token", "refresh_token"));
        when(refreshTokenRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        var response = authService.userRegister(validRequest);

        // THEN
        assertNotNull(response);
        assertEquals("access_token", response.getAccessToken());
        assertEquals("refresh_token", response.getRefreshToken());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void userRegister_PasswordMismatch_ShouldThrowBusinessException() {
        // GIVEN
        RegisterRequest request = TestDataFactory.createValidRegisterRequest();
        request.setConfirmPassword("different");

        // WHEN & THEN
        assertThrows(BusinessException.class, () -> authService.userRegister(request));
        verify(userRepository, never()).save(any());
    }

    @Test
    void userRegister_EmailAlreadyExists_ShouldThrowDuplicateResourceException() {
        // GIVEN
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // WHEN & THEN
        assertThrows(DuplicateResourceException.class,
                () -> authService.userRegister(validRequest));
        verify(userRepository, never()).save(any());
    }

    @Test
    void userRegister_UsernameAlreadyExists_ShouldThrowDuplicateResourceException() {
        // GIVEN
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // WHEN & THEN
        assertThrows(DuplicateResourceException.class,
                () -> authService.userRegister(validRequest));
        verify(userRepository, never()).save(any());
    }

    @Test
    void userRegister_PasswordTooShort_ShouldThrowBusinessException() {
        // GIVEN
        RegisterRequest request = TestDataFactory.createValidRegisterRequest();
        request.setPassword("1234567");

        // WHEN & THEN
        assertThrows(BusinessException.class, () -> authService.userRegister(request));
        verify(userRepository, never()).save(any());
    }
}
