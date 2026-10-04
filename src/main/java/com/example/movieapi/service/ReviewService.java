package com.example.movieapi.service;

import com.example.movieapi.dto.ReviewDto.ReviewRequest;
import com.example.movieapi.dto.ReviewDto.ReviewResponse;
import com.example.movieapi.entity.Movie;
import com.example.movieapi.entity.Review;
import com.example.movieapi.entity.User;
import com.example.movieapi.exception.AlreadyExistsException;
import com.example.movieapi.exception.NotFoundException;
import com.example.movieapi.mapper.ReviewMapper;
import com.example.movieapi.repository.MovieRepository;
import com.example.movieapi.repository.ReviewRepository;
import com.example.movieapi.service.iface.ReviewServiceIface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService implements ReviewServiceIface {

    private final UserService userService;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final MovieRepository movieRepository;

    @Override
    public ReviewResponse createReview(Long movieId, ReviewRequest reviewRequest) {

        User currentUser = userService.getCurrentUser();

        Movie currentMovie = movieRepository.findById(movieId)
                .orElseThrow(() -> new NotFoundException("Movie", movieId));

        if (reviewRepository.findByUserAndMovie(currentUser, currentMovie).isPresent()) {
            throw new AlreadyExistsException("Review", currentMovie.getTitle());
        }

        Review review = new Review();
        review.setUser(currentUser);
        review.setMovie(currentMovie);
        review.setText(reviewRequest.text());

        Review savedReview = reviewRepository.save(review);

        return reviewMapper.toReviewResponse(savedReview);
    }

    @Override
    public ReviewResponse updateReview(Long movieId, ReviewRequest reviewRequest) {

        User currentUser = userService.getCurrentUser();

        Movie currentMovie = movieRepository.findById(movieId)
                .orElseThrow(() -> new NotFoundException("Movie", movieId));

        Review review = reviewRepository.findByUserAndMovie(currentUser, currentMovie)
                .orElseThrow(() -> new NotFoundException("Review", movieId));

        review.setText(reviewRequest.text());

        Review savedReview = reviewRepository.save(review);

        return reviewMapper.toReviewResponse(savedReview);
    }

    @Override
    public void deleteReview(Long movieId) {

        User currentUser = userService.getCurrentUser();

        Movie currentMovie = movieRepository.findById(movieId)
                .orElseThrow(() -> new NotFoundException("Movie", movieId));

        Review review = reviewRepository.findByUserAndMovie(currentUser, currentMovie)
                .orElseThrow(() -> new NotFoundException("Review", movieId));

        reviewRepository.delete(review);
    }

    @Override
    public List<ReviewResponse> getReviewsByMovie(Long movieId) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new NotFoundException("Movie", movieId));

        List<Review> reviews = reviewRepository.findAllByMovie(movie);

        return reviewMapper.toReviewResponseList(reviews);
    }

    @Override
    public ReviewResponse getMyReview(Long movieId) {

        User currentUser = userService.getCurrentUser();

        Movie currentMovie = movieRepository.findById(movieId)
                .orElseThrow(() -> new NotFoundException("Movie", movieId));

        Review review = reviewRepository.findByUserAndMovie(currentUser, currentMovie)
                .orElseThrow(() -> new NotFoundException("Review", movieId));

        return reviewMapper.toReviewResponse(review);
    }
}