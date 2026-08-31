package com.example.movieapi.dto.RatingDto;

import java.time.Instant;

public record RatingResponse(Long id, Short rating , Long movieId, String title, Instant createdAt) {
}
