package com.example.movieapi.mapper;

import com.example.movieapi.dto.FavoriteDto.FavoriteResponse;
import com.example.movieapi.entity.Favorite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FavoriteMapper {

    @Mapping(source = "movie.id", target = "movieId")
    @Mapping(source = "movie.title", target = "title")
    @Mapping(source = "movie.posterPath", target = "posterPath")
    @Mapping(source = "movie.releaseDate", target = "releaseDate")
    @Mapping(source = "movie.voteAverage", target = "voteAverage")
    FavoriteResponse toFavoriteDto(Favorite favorite);

    List<FavoriteResponse> toFavoriteResponseList(List<Favorite> favorites);
}