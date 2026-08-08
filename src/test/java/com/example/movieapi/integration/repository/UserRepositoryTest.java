package com.example.movieapi.integration.repository;

import com.example.movieapi.entity.User;
import com.example.movieapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail_ShouldReturnUser_WhenExists() {
        // GIVEN
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("encoded");
        user.setFullname("Test User");
        user.setCreatedAt(Instant.now());
        user.setEnabled(true);
        userRepository.save(user);

        // WHEN
        Optional<User> found = userRepository.findByEmail("test@example.com");

        // THEN
        assertTrue(found.isPresent());
        assertEquals("test@example.com", found.get().getEmail());
    }

    @Test
    void existsByEmail_ShouldReturnTrue_WhenExists() {
        // GIVEN
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("encoded");
        user.setFullname("Test User");
        user.setCreatedAt(Instant.now());
        user.setEnabled(true);
        userRepository.save(user);

        // WHEN
        boolean exists = userRepository.existsByEmail("test@example.com");

        // THEN
        assertTrue(exists);
    }
}
