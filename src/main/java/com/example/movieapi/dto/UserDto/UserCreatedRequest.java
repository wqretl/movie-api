package com.example.movieapi.dto.UserDto;

public record UserCreatedRequest(
        String username,
        String email,
        String password,
        String fullName
) {
}
