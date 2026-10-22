package com.example.movieapi.dto.UserDto;

import jakarta.validation.constraints.NotNull;

public record UserStatusRequest(
        @NotNull
        Boolean enabled
) {
}
