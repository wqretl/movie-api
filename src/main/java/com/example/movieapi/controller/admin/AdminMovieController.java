package com.example.movieapi.controller.admin;

import com.example.movieapi.dto.MovieDto.MovieResponse;
import com.example.movieapi.dto.MovieDto.MovieUpdateRequest;
import com.example.movieapi.entity.Movie;
import com.example.movieapi.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
@RestController
@RequestMapping("/api/admin/movies")
@RequiredArgsConstructor
public class AdminMovieController {



        private final MovieService movieService;


        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteById(@PathVariable Long id){
            movieService.deleteMovieById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        @PostMapping
        public ResponseEntity<MovieResponse> createMovie(@Valid @RequestBody Movie movie){

            MovieResponse movieResponse = movieService.createMovie(movie);

            return ResponseEntity
                    .created(URI.create("/api/movies/"+ movieResponse.id()))
                    .body(movieResponse);


        }

        @PatchMapping("/{id}")
        public ResponseEntity<MovieResponse> updateMovie(@PathVariable Long id, @RequestBody MovieUpdateRequest movie){
            MovieResponse movieResponse = movieService.updateMovie(id, movie);
            return ResponseEntity.status(HttpStatus.OK).body(movieResponse);
        }
    }

