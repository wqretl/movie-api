package com.example.movieapi.repository;

import com.example.movieapi.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long> {

    List<Movie> findByTitleContainingIgnoreCase(String title);

    boolean existsByImdbId(String imdbId);

    boolean existsByImdbIdAndIdNot(String imdbId, Long id);

    List<Movie> findByVoteAverageGreaterThanEqual(BigDecimal voteAverage);

    List<Movie> findByReleaseDateAfter(LocalDate releaseDate);

    List<Movie>findByVoteAverageGreaterThanEqualAndReleaseDateAfter(
            BigDecimal voteAverage,
            LocalDate releaseDate);

    List<Movie> findByOverview(String overview);
}
