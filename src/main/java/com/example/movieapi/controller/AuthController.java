package com.example.movieapi.controller;

import com.example.movieapi.dto.AuthResponse;
import com.example.movieapi.dto.LoginRequest;
import com.example.movieapi.dto.RegisterRequest;
import com.example.movieapi.exception.BusinessException;
import com.example.movieapi.service.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    /**
     * Регистрация нового пользователя
     * @param request данные для регистрации
     * @return AuthResponse с токенами доступа
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.userRegister(request));
    }

    /**
     * Вход в систему
     * @param request email и пароль
     * @return AuthResponse с токенами доступа
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.userLogin(request));
    }

    /**
     * Обновление Access Token через Refresh Token
     * @param authorizationHeader заголовок с Refresh Token
     * @return AuthResponse с новым Access Token
     */
    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(
            @RequestHeader("Authorization") String authorizationHeader) {
        String refreshToken = extractToken(authorizationHeader);
        return ResponseEntity.ok(authService.refreshAccessToken(refreshToken));
    }

    /**
     * Выход из системы (отзыв Refresh Token)
     * @param authorizationHeader заголовок с Refresh Token
     * @return 200 OK
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader("Authorization") String authorizationHeader) {
        String refreshToken = extractToken(authorizationHeader);
        authService.logout(refreshToken);
        return ResponseEntity.ok().build();
    }

    //ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ

    /**
     * Извлечение токена из заголовка Authorization
     * @param header заголовок Authorization
     * @return токен без префикса "Bearer "
     */
    private String extractToken(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        throw new BusinessException("Invalid authorization header");
    }
}
