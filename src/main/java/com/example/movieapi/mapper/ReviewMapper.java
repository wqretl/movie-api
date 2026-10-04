package com.example.movieapi.mapper;


import com.example.movieapi.dto.ReviewDto.ReviewResponse;
import com.example.movieapi.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper (componentModel = "spring")
public interface ReviewMapper {
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "movie.id", target = "movieId")
    @Mapping(source = "movie.title", target = "movieTitle")
    ReviewResponse toReviewResponse(Review review);

    List<ReviewResponse> toReviewResponseList(List<Review> reviews);
}
