package com.example.movieapi.controller;


import com.example.movieapi.dto.MovieResponse;
import com.example.movieapi.dto.MovieUpdateRequest;
import com.example.movieapi.entity.Movie;
import com.example.movieapi.repository.MovieRepository;
import com.example.movieapi.service.MovieService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/movie")
@AllArgsConstructor

public class MovieController {

    private final MovieService movieService;
    private final MovieRepository movieRepository;



    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getById(@PathVariable Long id){

        return  ResponseEntity.status(HttpStatus.OK).body(movieService.getMovieById(id));

    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        movieService.deleteMovieById(id);
    }

    @PostMapping
    public ResponseEntity<MovieResponse> createMovie( @Valid @RequestBody Movie movie){

        MovieResponse movieResponse = movieService.createMovie(movie);

        return ResponseEntity.created(URI.create("/api/movie/"+ movieResponse.id())).body(movieResponse);


    }

    @PatchMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable Long id, @RequestBody MovieUpdateRequest movie){
        MovieResponse movieResponse = movieService.updateMovie(id, movie);
        return ResponseEntity.status(HttpStatus.OK).body(movieResponse);
    }

    @GetMapping ("/search")
    public ResponseEntity<List<MovieResponse>> searchByTitle(@RequestParam String title){

        return ResponseEntity.status(HttpStatus.OK).body(movieService.searchByTitle(title));

    }


    @GetMapping ("/filter")
    public ResponseEntity<List<MovieResponse>> filterByReleaseDate(
            @RequestParam (required = false) LocalDate releaseDate,
            @RequestParam (required = false)BigDecimal averageVote)
    {
            return ResponseEntity.status(HttpStatus.OK).body(movieService.filterMovies(averageVote,releaseDate));
    }

    @GetMapping("/sort")
    public ResponseEntity<List<MovieResponse>> sortMovies(
            @RequestParam String field,
            @RequestParam (defaultValue = "asc") String direction){

        return ResponseEntity.status(HttpStatus.OK).body(movieService.sortMovies(field,direction));

    }


    @GetMapping("/page")
    public ResponseEntity<Page<MovieResponse>> getMoviesByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String field,
            @RequestParam(defaultValue = "asc") String direction
    ) {

        return ResponseEntity.ok(
                movieService.getMoviesByPage(
                        page,
                        size,
                        field,
                        direction
                )
        );
    }





}
