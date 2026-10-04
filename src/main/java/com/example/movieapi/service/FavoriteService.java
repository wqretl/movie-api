package com.example.movieapi.service;

import com.example.movieapi.dto.FavoriteDto.FavoriteResponse;
import com.example.movieapi.entity.Favorite;
import com.example.movieapi.entity.Movie;
import com.example.movieapi.entity.User;
import com.example.movieapi.exception.DuplicateResourceException;
import com.example.movieapi.exception.NotFoundException;
import com.example.movieapi.mapper.FavoriteMapper;
import com.example.movieapi.repository.FavoriteRepository;
import com.example.movieapi.repository.MovieRepository;
import com.example.movieapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class FavoriteService {

    private FavoriteRepository favoriteRepository;
    private MovieRepository movieRepository;
    private UserService userService;
    private  FavoriteMapper favoriteMapper ;


    public FavoriteResponse addFavorite(Long movieId) {
        User user = userService.getCurrentUser();

        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new NotFoundException("Movie", movieId));

        if (favoriteRepository.existsByUserAndMovie(user, movie)) {

            throw new DuplicateResourceException("Favorite already exists in FavoriteService");

        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setMovie(movie);

        favoriteRepository.save(favorite);

        FavoriteResponse favoriteResponse =
                favoriteMapper.toFavoriteDto(favorite);

        return favoriteResponse;


    }

    public List<FavoriteResponse> getFavorites() {

        User user = userService.getCurrentUser();

        List <Favorite> favorites = favoriteRepository.findAllByUser(user);

        List<FavoriteResponse> favoriteResponseList = favoriteMapper.toFavoriteResponseList(favorites);

        return favoriteResponseList;

    }

    public void removeFavorite(Long movieId) {
        User user = userService.getCurrentUser();

        Movie  movie = movieRepository.findById(movieId).orElseThrow(() -> new NotFoundException("Movie", movieId));

        Favorite favorite = favoriteRepository
                .findByUserAndMovie(user, movie)
                    .orElseThrow(()->
                                new NotFoundException("Movie",movieId));

        favoriteRepository.delete(favorite);

    }

}
