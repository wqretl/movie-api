package com.example.movieapi.service;


import com.example.movieapi.dto.MovieDto.MovieResponse;
import com.example.movieapi.dto.MovieDto.MovieUpdateRequest;
import com.example.movieapi.entity.Movie;
import com.example.movieapi.exception.DuplicateResourceException;
import com.example.movieapi.exception.NotFoundException;
import com.example.movieapi.mapper.MovieMapper;
import com.example.movieapi.repository.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;


    public MovieResponse createMovie(Movie movie) {

        if (movie.getImdbId() != null && movieRepository.existsByImdbId(movie.getImdbId())) {
            throw new DuplicateResourceException("Movie is already exist");
        }

        Movie savedMovie = movieRepository.save(movie);

        return movieMapper.toResponse(savedMovie);


    }

    @Transactional
    public void deleteMovieById(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new NotFoundException("Movie", movieId));

        movieRepository.delete(movie);
    }

    public MovieResponse getMovieById(Long movieId) {

        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new NotFoundException("Movie",movieId));

        return movieMapper.toResponse(movie);

    }




    public MovieResponse updateMovie(Long movieId, MovieUpdateRequest movie) {
        Movie findMovie = movieRepository.findById(movieId)
                .orElseThrow(() -> new NotFoundException("Movie", movieId));

        if (movie.title() != null) findMovie.setTitle(movie.title());
        if (movie.originalTitle() != null) findMovie.setOriginalTitle(movie.originalTitle());
        if (movie.releaseDate() != null) findMovie.setReleaseDate(movie.releaseDate());
        if (movie.voteAverage() != null) findMovie.setVoteAverage(movie.voteAverage());
        if (movie.voteCount() != null) findMovie.setVoteCount(movie.voteCount());
        if (movie.overview() != null) findMovie.setOverview(movie.overview());
        if (movie.posterPath() != null) findMovie.setPosterPath(movie.posterPath());
        if (movie.backdropPath() != null) findMovie.setBackdropPath(movie.backdropPath());
        if (movie.popularity() != null) findMovie.setPopularity(movie.popularity());
        if (movie.adult() != null) findMovie.setAdult(movie.adult());
        if (movie.video() != null) findMovie.setVideo(movie.video());
        if (movie.budget() != null) findMovie.setBudget(movie.budget());
        if (movie.revenue() != null) findMovie.setRevenue(movie.revenue());
        if (movie.runtime() != null) findMovie.setRuntime(movie.runtime());
        if (movie.status() != null) findMovie.setStatus(movie.status());
        if (movie.tagline() != null) findMovie.setTagline(movie.tagline());
        if (movie.imdbId() != null) {
            if (movieRepository.existsByImdbIdAndIdNot(movie.imdbId(), movieId)) {
                throw new DuplicateResourceException("Movie is already exist");
            }
            findMovie.setImdbId(movie.imdbId());
        }
        if (movie.originalLanguage() != null) findMovie.setOriginalLanguage(movie.originalLanguage());
        if (movie.homepage() != null) findMovie.setHomepage(movie.homepage());

        Movie updatedMovie = movieRepository.save(findMovie);
        return movieMapper.toResponse(updatedMovie);
    }

    public List<MovieResponse> searchByTitle(String title){
        List<MovieResponse> movies = new ArrayList<>();
        for (Movie movie : movieRepository.findByTitleContainingIgnoreCase(title)) {
            MovieResponse movieResponse = movieMapper.toResponse(movie);
            movies.add(movieResponse);
        }
        return movies;
    }



    public List<MovieResponse> filterMovies(BigDecimal voteAverage, LocalDate releaseDate) {

      if (voteAverage != null && releaseDate != null) {
          return movieMapper.toMovieResponseList(movieRepository.findByVoteAverageGreaterThanEqual(voteAverage));
      }

      if (voteAverage == null && releaseDate != null) {
          return movieMapper.toMovieResponseList(movieRepository.findByReleaseDateAfter(releaseDate));

      }

      if (voteAverage != null && releaseDate != null) {
          return movieMapper.toMovieResponseList(movieRepository.findByVoteAverageGreaterThanEqualAndReleaseDateAfter(voteAverage, releaseDate));
      }

      return movieMapper.toMovieResponseList(movieRepository.findAll());
    }


    // todo Нужна доработка!!! Если пользователь попробует отсортировать фильмы по бюджету , то сортировка пойдет или какое-то не то поле (id,и тд )

    public List<MovieResponse> sortMovies( String field, String direction) {

        Sort.Direction sortDirection ;
        if (direction.equals("asc")) {
            sortDirection = Sort.Direction.ASC;
        }else if (direction.equals("desc")) {
            sortDirection = Sort.Direction.DESC;
        }
        else  {
            throw new IllegalArgumentException("Invalid direction"+direction);
        }
        Sort sort = Sort.by(sortDirection,field);

        return movieMapper.toMovieResponseList(movieRepository.findAll(sort));

    }


    public Page<MovieResponse> getMoviesByPage(
            int page,
            int size,
            String field,
            String direction
    ) {

        Sort.Direction sortDirection;

        if (direction.equals("asc")) {
            sortDirection = Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            sortDirection = Sort.Direction.DESC;
        } else {
            throw new IllegalArgumentException(
                    "Invalid direction: " + direction
            );
        }

        Sort sort = Sort.by(sortDirection, field);

        Pageable pageable = PageRequest.of(
                page,
                size,
                sort
        );

        return movieRepository.findAll(pageable)
                .map(movieMapper::toResponse);
    }


}
