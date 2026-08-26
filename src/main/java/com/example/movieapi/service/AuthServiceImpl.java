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
                .orElseThrow(() -> new ResourceNotFoundException("The role USER was not found in the system"));

        Instant now = Instant.now();
        User user = new User();
        user.setUsername(request.getUsername());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setEnabled(true);
        user.setCreatedAt(now);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.addRole(userRole);

        User savedUser = userRepository.save(user);

        TokenPair tokenPair = jwtTokenProvider.generateAndSaveTokens(savedUser);

        String role = savedUser.getRoles().stream()
                .findFirst()
                .map(Role::getName)
                .orElse("USER");

        return AuthResponse.builder()
                .accessToken(tokenPair.accessToken())
                .refreshToken(tokenPair.refreshToken())
                .tokenType("Bearer")
                .expiresIn(15 * 60L)
                .role(role)  // ← Добавили
                .username(savedUser.getUsername())
                .build();
    }

    @Transactional
    public AuthResponse userLogin(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new BusinessException("Invalid password");


        if (!user.getEnabled())
            throw new BusinessException("Account is disabled");


        TokenPair tokenPair = jwtTokenProvider.generateAndSaveTokens(user);

        String role = user.getRoles().stream()
                .findFirst()
                .map(Role::getName)
                .orElse("USER");

        return AuthResponse.builder()
                .accessToken(tokenPair.accessToken())
                .refreshToken(tokenPair.refreshToken())
                .tokenType("Bearer")
                .expiresIn(15 * 60L)
                .role(role)  // ← Добавили
                .username(user.getUsername())
                .build();
    }

    @Transactional
    public String refreshAccessToken(String refreshToken) {

        // 1. Проверяем JWT
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BusinessException("Invalid refresh token");
        }

        // 2. Извлекаем данные
        String jti = jwtTokenProvider.getJwtId(refreshToken);
        Long userId = jwtTokenProvider.getUserIdFromToken(refreshToken);

        // 3. Ищем по jti
        RefreshToken token = refreshTokenRepository.findByJti(jti)
                .orElseThrow(() -> new ResourceNotFoundException("Refresh token not found"));

        // 4. Проверяем принадлежность
        if (!token.getUser().getId().equals(userId)) {
            throw new BusinessException("Token does not belong to this user");
        }

        // 5. Проверяем хеш JTI (НЕ ВЕСЬ ТОКЕН!)
        if (!passwordEncoder.matches(jti, token.getTokenHash())) {
            throw new BusinessException("Token hash mismatch");
        }

        // 6. Проверяем статус
        if (!token.isValid()) {
            throw new BusinessException("Token has been revoked or expired");
        }

        // 7. Получаем пользователя
        User tokenOwner = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!tokenOwner.getEnabled()) {
            throw new BusinessException("User account is disabled");
        }

        return jwtTokenProvider.generateAccessToken(tokenOwner);
    }

    public void logout(String refreshToken) {

        // 1. Проверяем валидность токена
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BusinessException("Invalid refresh token");
        }

        // 2. Извлекаем JTI из токена
        String jti = jwtTokenProvider.getJwtId(refreshToken);

        // 3. Ищем токен в БД
        RefreshToken token = refreshTokenRepository.findByJti(jti)
                .orElseThrow(() -> new ResourceNotFoundException("Refresh token not found"));

        // 4. Отзываем токен
        token.setRevoked(true);
        token.setRevokedAt(Instant.now());
        refreshTokenRepository.save(token);
    }

    private void validateRegistration(RegisterRequest request) {

        if (!Objects.equals(request.getPassword(), request.getConfirmPassword()))
            throw new BusinessException("Password do not match");


        if (request.getPassword().length() < 8)
            throw new BusinessException("The password must contain at least 8 characters.");


        if (userRepository.existsByEmail(request.getEmail()))
            throw new DuplicateResourceException("User with this email already exists");


        if (userRepository.existsByUsername(request.getUsername()))
            throw new DuplicateResourceException("A user with the same name " + request.getUsername() + " already exists.");

    }
}
