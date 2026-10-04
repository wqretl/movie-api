package com.example.movieapi.service;

import com.example.movieapi.dto.RatingDto.RatingRequest;
import com.example.movieapi.dto.RatingDto.RatingResponse;
import com.example.movieapi.entity.Movie;
import com.example.movieapi.entity.Rating;
import com.example.movieapi.entity.User;
import com.example.movieapi.exception.AlreadyExistsException;
import com.example.movieapi.exception.NotFoundException;
import com.example.movieapi.mapper.FavoriteMapper;
import com.example.movieapi.mapper.RatingMapper;
import com.example.movieapi.repository.MovieRepository;
import com.example.movieapi.repository.RatingRepository;
import com.example.movieapi.service.iface.RatingServiceIface;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingService implements RatingServiceIface {

    private final UserService userService;
    private final RatingMapper ratingMapper;
    private final RatingRepository ratingRepository;
    private final MovieRepository movieRepository;



    @Override
    public RatingResponse createRating(RatingRequest ratingRequest, Long movieId) {

        User currentUser = userService.getCurrentUser();

        Movie currentMovie = movieRepository.findById(movieId).orElseThrow(()-> new NotFoundException("Movie", movieId));

        if (ratingRepository.existsByUserAndMovie(currentUser, currentMovie)) {
            throw new AlreadyExistsException("Rating",currentMovie.getTitle());
        }

        Rating rating = new Rating();
        rating.setUser(currentUser);
        rating.setMovie(currentMovie);
        rating.setRating(ratingRequest.rating());
        Rating savedRating =ratingRepository.save(rating);

        return ratingMapper.toRatingResponse(savedRating);

    }

    @Override
    public RatingResponse updateRating(RatingRequest request, Long movieId) {
        User currentUser = userService.getCurrentUser();

        Movie currentMovie = movieRepository.findById(movieId).orElseThrow(()-> new NotFoundException("Movie", movieId));

        Rating rating = ratingRepository.findByUserAndMovie(currentUser,currentMovie).orElseThrow(()->new NotFoundException("Rating",movieId));
        rating.setRating(request.rating());
        Rating savedRating = ratingRepository.save(rating);
        return ratingMapper.toRatingResponse(savedRating);

    }

    @Override
    public void deleteRating(Long movieId) {

        User currentUser = userService.getCurrentUser();

        Movie currentMovie = movieRepository.findById(movieId).orElseThrow(()-> new NotFoundException("Movie", movieId));

        Rating rating = ratingRepository.findByUserAndMovie(currentUser,currentMovie).orElseThrow(()->new NotFoundException("Rating",movieId));

        ratingRepository.delete(rating);

    }

    @Override
    public RatingResponse getRatingByMovieId(Long movieId) {

        User currentUser = userService.getCurrentUser();

        Movie currentMovie = movieRepository.findById(movieId).orElseThrow(()-> new NotFoundException("Movie", movieId));

        Rating currentRate =  ratingRepository.findByUserAndMovie(currentUser,currentMovie).orElseThrow(()->new NotFoundException("Rating",movieId));

        return ratingMapper.toRatingResponse(currentRate);

    }
}
