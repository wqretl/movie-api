package com.example.movieapi.dto.UserDto;

import com.example.movieapi.entity.Role;

import java.time.Instant;
import java.util.Set;

public record UserProfileResponse (
        Long id,
        String username,
        String email,
        boolean enabled,
        Instant createdAt,
        String fullName,
        boolean isEmailVerified,
        Set<String> roles

) {
}
