package com.example.movieapi.repository;

import com.example.movieapi.entity.Movie;
import com.example.movieapi.entity.Rating;
import com.example.movieapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating,Long> {

    Optional<Rating> findByUserAndMovie(User user, Movie movie);

    boolean existsByUserAndMovie(User user, Movie movie);
}
