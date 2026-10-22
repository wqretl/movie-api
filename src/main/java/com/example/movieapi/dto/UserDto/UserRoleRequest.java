package com.example.movieapi.dto.UserDto;

import jakarta.validation.constraints.NotBlank;

public record UserRoleRequest
        (
             @NotBlank
             String role
        ){
}
