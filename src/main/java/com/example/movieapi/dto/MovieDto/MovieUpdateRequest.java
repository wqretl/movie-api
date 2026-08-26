package com.example.movieapi.dto.MovieDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MovieUpdateRequest(
        String title,
        String originalTitle,
        LocalDate releaseDate,
        BigDecimal voteAverage,
        Integer voteCount,
        String overview,
        String posterPath,
        String backdropPath,
        BigDecimal popularity,
        Boolean adult,
        Boolean video,
        Long budget,
        Long revenue,
        Integer runtime,
        String status,
        String tagline,
        String imdbId,
        String originalLanguage,
        String homepage
) {
}
