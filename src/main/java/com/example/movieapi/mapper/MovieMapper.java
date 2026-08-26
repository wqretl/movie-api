package com.example.movieapi.mapper;

import com.example.movieapi.dto.MovieDto.MovieRequest;
import com.example.movieapi.dto.MovieDto.MovieResponse;
import com.example.movieapi.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    Movie toMovie(MovieRequest movieRequest);

    MovieRequest toMovieRequest(Movie movie);

    MovieResponse toResponse(Movie movie);

    void updateEntity(MovieRequest movieRequest, @MappingTarget Movie movie);

    List<MovieResponse> toMovieResponseList(List<Movie> movies);



}
