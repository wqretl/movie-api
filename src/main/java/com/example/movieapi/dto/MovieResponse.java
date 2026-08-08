package com.example.movieapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MovieResponse(

        Long id,

        String title,

        LocalDate releaseDate,

        BigDecimal voteAverage,

        Integer runtime,

        String posterPath

) {
}
