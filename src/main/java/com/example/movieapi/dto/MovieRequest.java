package com.example.movieapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record MovieRequest(

        @NotBlank
        String title,

        String originalTitle,

        @NotNull
        LocalDate releaseDate,

        String overview,

        String posterPath,

        String backdropPath,

        @Positive
        Integer runtime,

        String status,

        String tagline,

        String originalLanguage,

        Long budget,

        Long revenue,

        String imdbId,

        String homepage,

        Boolean adult,

        Boolean video

) {
}