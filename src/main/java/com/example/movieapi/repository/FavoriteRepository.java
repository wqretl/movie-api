package com.example.movieapi.repository;

import com.example.movieapi.entity.Favorite;
import com.example.movieapi.entity.Movie;
import com.example.movieapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite,Long> {

    boolean existsByUserAndMovie (User user, Movie movie);

    List<Favorite> findAllByUser (User user);

    Optional<Favorite> findByUserAndMovie (User user, Movie movie);

  void deleteByUserIdAndMovieId (Long userId, Long movieId);
}
