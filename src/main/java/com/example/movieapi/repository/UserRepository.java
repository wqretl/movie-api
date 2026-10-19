package com.example.movieapi.repository;

import com.example.movieapi.entity.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends Repository<User, Long> {

    boolean existsByEmail(String email);

    User save(User user);

    List<User> findAll();

    boolean existsByUsername(@NotBlank(message = "Username обязателен!") String username);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    void delete(User user);
}
