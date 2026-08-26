package com.example.movieapi.dto.UserDto;

import java.time.Instant;

public record UserProfileResponse (
        Long id,
        String username,
        String email,
        Instant createdAt,
        String fullName,
        boolean isEmailVerified
) {
}
