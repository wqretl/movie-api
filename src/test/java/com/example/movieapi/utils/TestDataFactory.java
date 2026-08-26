package com.example.movieapi.utils;

import com.example.movieapi.dto.RegisterRequest;
import com.example.movieapi.entity.Role;
import com.example.movieapi.entity.User;
import com.example.movieapi.model.enums.RoleType;
import org.springframework.security.core.parameters.P;

import java.time.Instant;
import java.util.HashSet;

public class TestDataFactory {
    public static RegisterRequest createValidRegisterRequest(){
        RegisterRequest request = new RegisterRequest();
        request.setFullName("Alex Smirnov");
        request.setUsername("alexsmirnov");
        request.setEmail("alexsmirnov@gmail.com");
        request.setPassword("password123");
        request.setConfirmPassword("password123");

        return request;
    }

    public static User createTestUser(){

        User user = new User();
        user.setId(1L);
        user.setUsername("alexsmirnov");
        user.setFullName("Alex Smirnov");
        user.setEmail("alexsmirnov@gmail.com");
        user.setPassword("$2a$12$IyZa/gTWHpoEtZmYZQMOVuHY.LkTOEJ7af7j6ZzYLBipc0SHwBcLq");
        user.setEnabled(true);
        user.setCreatedAt(Instant.now());
        user.setRoles(new HashSet<>());
        return user;
    }

    public static Role createUserRole(){
        Role role = new Role();
        role.setId(1);
        role.setName(RoleType.USER.getName());

        return role;
    }
}
