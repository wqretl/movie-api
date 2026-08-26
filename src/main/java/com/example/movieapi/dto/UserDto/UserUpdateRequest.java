package com.example.movieapi.dto.UserDto;

public record UserUpdateRequest(
        String username,
        String fullName
) {
}
