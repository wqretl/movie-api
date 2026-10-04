package com.example.movieapi.dto.ReviewDto;

import java.time.Instant;

public record ReviewResponse(
        Long id,
        String username ,
        Long movieId,
        String movieTitle,
        String text,
        Instant createdAt,
        Instant updatedAt
) {
}
