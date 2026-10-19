package com.example.movieapi.service;

import com.example.movieapi.dto.AuthResponse;
import com.example.movieapi.dto.LoginRequest;
import com.example.movieapi.dto.RegisterRequest;
import com.example.movieapi.dto.TokenPair;
import com.example.movieapi.entity.RefreshToken;
import com.example.movieapi.entity.Role;
import com.example.movieapi.entity.User;
import com.example.movieapi.exception.BusinessException;
import com.example.movieapi.exception.DuplicateResourceException;
import com.example.movieapi.exception.ResourceNotFoundException;
import com.example.movieapi.repository.RefreshTokenRepository;
import com.example.movieapi.repository.RoleRepository;
import com.example.movieapi.repository.UserRepository;
import com.example.movieapi.security.jwt.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class AuthServiceImpl {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;


    @Transactional
    public AuthResponse userRegister(RegisterRequest request) {

        validateRegistration(request);

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "The role USER was not found in the system"
                        )
                );

        User user = new User();

        user.setUsername(request.getUsername());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.addRole(userRole);

        User savedUser = userRepository.save(user);

        TokenPair tokenPair =
                jwtTokenProvider.generateAndSaveTokens(savedUser);

        List<String> roles = savedUser.getRoles().stream()
                .map(Role::getName)
                .toList();

        return AuthResponse.builder()
                .accessToken(tokenPair.accessToken())
                .refreshToken(tokenPair.refreshToken())
                .tokenType("Bearer")
                .expiresIn(15 * 60L)
                .roles(roles)
                .username(savedUser.getUsername())
                .build();
    }


    @Transactional
    public AuthResponse userLogin(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found")
                );

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new BusinessException("Invalid password");
        }

        if (!user.getEnabled()) {
            throw new BusinessException("Account is disabled");
        }

        TokenPair tokenPair =
                jwtTokenProvider.generateAndSaveTokens(user);

        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .toList();

        return AuthResponse.builder()
                .accessToken(tokenPair.accessToken())
                .refreshToken(tokenPair.refreshToken())
                .tokenType("Bearer")
                .expiresIn(15 * 60L)
                .roles(roles)
                .username(user.getUsername())
                .build();
    }


    @Transactional
    public String refreshAccessToken(String refreshToken) {

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BusinessException("Invalid refresh token");
        }

        String jti = jwtTokenProvider.getJwtId(refreshToken);
        Long userId = jwtTokenProvider.getUserIdFromToken(refreshToken);

        RefreshToken token = refreshTokenRepository.findByJti(jti)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Refresh token not found"
                        )
                );

        if (!token.getUser().getId().equals(userId)) {
            throw new BusinessException(
                    "Token does not belong to this user"
            );
        }

        if (!passwordEncoder.matches(
                jti,
                token.getTokenHash()
        )) {
            throw new BusinessException("Token hash mismatch");
        }

        if (!token.isValid()) {
            throw new BusinessException(
                    "Token has been revoked or expired"
            );
        }

        User tokenOwner = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );

        if (!tokenOwner.getEnabled()) {
            throw new BusinessException(
                    "User account is disabled"
            );
        }

        return jwtTokenProvider.generateAccessToken(tokenOwner);
    }


    public void logout(String refreshToken) {

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BusinessException("Invalid refresh token");
        }

        String jti = jwtTokenProvider.getJwtId(refreshToken);

        RefreshToken token = refreshTokenRepository.findByJti(jti)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Refresh token not found"
                        )
                );

        token.setRevoked(true);
        token.setRevokedAt(Instant.now());

        refreshTokenRepository.save(token);
    }


    private void validateRegistration(RegisterRequest request) {

        if (!Objects.equals(
                request.getPassword(),
                request.getConfirmPassword()
        )) {
            throw new BusinessException(
                    "Passwords do not match"
            );
        }

        if (request.getPassword().length() < 8) {
            throw new BusinessException(
                    "The password must contain at least 8 characters."
            );
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "User with this email already exists"
            );
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException(
                    "A user with the same name "
                            + request.getUsername()
                            + " already exists."
            );
        }
    }
}