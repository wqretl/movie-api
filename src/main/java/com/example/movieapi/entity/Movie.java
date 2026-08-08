package com.example.movieapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "movies")
public class Movie {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = Integer.MAX_VALUE)
    private String title;

    @Column(name = "original_title", length = Integer.MAX_VALUE)
    private String originalTitle;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "vote_average", precision = 10, scale = 2)
    private BigDecimal voteAverage;

    @Column(name = "vote_count")
    private Integer voteCount;

    @Column(name = "overview", length = Integer.MAX_VALUE)
    private String overview;

    @Column(name = "poster_path", length = Integer.MAX_VALUE)
    private String posterPath;

    @Column(name = "backdrop_path", length = Integer.MAX_VALUE)
    private String backdropPath;

    @Column(name = "popularity", precision = 10, scale = 2)
    private BigDecimal popularity;

    @Column(name = "adult")
    private Boolean adult;

    @Column(name = "video")
    private Boolean video;

    @Column(name = "budget")
    private Long budget;

    @Column(name = "revenue")
    private Long revenue;

    @Column(name = "runtime")
    private Integer runtime;

    @Column(name = "status", length = Integer.MAX_VALUE)
    private String status;

    @Column(name = "tagline", length = Integer.MAX_VALUE)
    private String tagline;

    @Column(name = "imdb_id", length = Integer.MAX_VALUE, unique = true)
    private String imdbId;

    @Column(name = "original_language", length = Integer.MAX_VALUE)
    private String originalLanguage;

    @Column(name = "homepage", length = Integer.MAX_VALUE)
    private String homepage;

}
