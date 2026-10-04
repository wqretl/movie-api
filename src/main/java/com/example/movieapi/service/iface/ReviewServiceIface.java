package com.example.movieapi.service.iface;

import com.example.movieapi.dto.ReviewDto.ReviewRequest;
import com.example.movieapi.dto.ReviewDto.ReviewResponse;

import java.util.List;

public interface ReviewServiceIface {

   ReviewResponse createReview  (Long movieId, ReviewRequest reviewRequest);

   ReviewResponse updateReview(Long movieId, ReviewRequest reviewRequest);

   void deleteReview(Long movieId);


   List<ReviewResponse> getReviewsByMovie(Long movieId);

   ReviewResponse getMyReview(Long movieId);

}
