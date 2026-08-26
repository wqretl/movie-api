package com.example.movieapi.dto.FavoriteDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record FavoriteResponse(
        Long id,
        Long movieId,
        String title,
        String posterPath,
        LocalDate releaseDate,
        BigDecimal voteAverage,
        Instant createdAt
) {
}