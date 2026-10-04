package com.example.movieapi.repository;

import com.example.movieapi.entity.Movie;
import com.example.movieapi.entity.Review;
import com.example.movieapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByUserAndMovie(User user, Movie movie);

    List<Review> findAllByMovie(Movie movie);
}
