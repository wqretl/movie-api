package com.example.movieapi.controller;

import com.example.movieapi.dto.ReviewDto.ReviewRequest;
import com.example.movieapi.dto.ReviewDto.ReviewResponse;
import com.example.movieapi.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {


    private final ReviewService reviewService;


    @PostMapping("/{movieId}")
    public ResponseEntity<ReviewResponse> createReview(@RequestBody ReviewRequest reviewRequest, @PathVariable Long movieId)

    {

    ReviewResponse reviewResponse = reviewService.createReview(movieId, reviewRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(reviewResponse);
    }

    @PatchMapping("/{movieId}")
    public ResponseEntity<ReviewResponse> updateReview (@RequestBody ReviewRequest reviewRequest, @PathVariable Long movieId){
        ReviewResponse reviewResponse = reviewService.updateReview(movieId, reviewRequest);
        return ResponseEntity.status(HttpStatus.OK).body(reviewResponse);

    }

    @GetMapping("/my/{movieId}")
    public ResponseEntity<ReviewResponse> getReview(@PathVariable Long movieId){

        ReviewResponse reviewResponse= reviewService.getMyReview(movieId);
        return ResponseEntity.status(HttpStatus.OK).body(reviewResponse);
    }

    @DeleteMapping ("/{movieId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long movieId){
        reviewService.deleteReview(movieId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping ("/movie/{movieId}")
    public ResponseEntity<List<ReviewResponse>> getMovieReview(@PathVariable Long movieId){

        List< ReviewResponse> reviewResponses = reviewService.getReviewsByMovie(movieId);
        return ResponseEntity.status(HttpStatus.OK).body(reviewResponses);

    }

}
