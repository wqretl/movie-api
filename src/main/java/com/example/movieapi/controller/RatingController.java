package com.example.movieapi.controller;

import com.example.movieapi.dto.RatingDto.RatingRequest;
import com.example.movieapi.dto.RatingDto.RatingResponse;
import com.example.movieapi.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ratings")
public class RatingController {
    private final RatingService ratingService;



@PostMapping("/{movieId}")
    public ResponseEntity<RatingResponse> createRating  (@Valid @RequestBody RatingRequest ratingRequest, @PathVariable Long movieId) {
    RatingResponse ratingResponse= ratingService.createRating(ratingRequest, movieId);
    return ResponseEntity.status(HttpStatus.CREATED).body(ratingResponse);
}


@GetMapping("/{movieId}")
    public ResponseEntity<RatingResponse> getRatingByMovieId(@PathVariable Long movieId) {

    return ResponseEntity.status(HttpStatus.OK).body(ratingService.getRatingByMovieId(movieId));


}

@DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long movieId) {

   ratingService.deleteRating(movieId);
   return ResponseEntity.noContent().build();

}

@PatchMapping("/{movieId}")
    public ResponseEntity<RatingResponse> updateRating(@Valid @RequestBody RatingRequest ratingRequest, @PathVariable Long movieId) {
    return ResponseEntity.status(HttpStatus.OK).body(ratingService.updateRating(ratingRequest, movieId));

}

}
