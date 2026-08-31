package com.example.movieapi.service.iface;

import com.example.movieapi.dto.RatingDto.RatingRequest;
import com.example.movieapi.dto.RatingDto.RatingResponse;


public interface RatingServiceIface {

    RatingResponse createRating(RatingRequest ratingRequest, Long movieId);

    RatingResponse updateRating(RatingRequest request, Long movieId);

    void deleteRating(Long movieId);

    RatingResponse getRatingByMovieId(Long movieId);

}
