package com.example.movieapi.dto.RatingDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RatingRequest(@NotNull @Min(1) @Max(10) Short rating) {
}
