package com.example.movieapi.mapper;

import com.example.movieapi.dto.RatingDto.RatingResponse;
import com.example.movieapi.entity.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface  RatingMapper {
    @Mapping(source = "movie.id", target = "movieId")
    @Mapping(source = "movie.title", target = "title")
    RatingResponse toRatingResponse(Rating rating);

}
