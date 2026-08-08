package com.example.movieapi.mapper;

import com.example.movieapi.dto.MovieRequest;
import com.example.movieapi.dto.MovieResponse;
import com.example.movieapi.entity.Movie;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieMapper {

    public Movie toMovie (MovieRequest movieRequest) {

        Movie movie = new Movie();
        movie.setTitle(movieRequest.title());
        movie.setOriginalTitle(movieRequest.originalTitle());
        movie.setReleaseDate(movieRequest.releaseDate());
        movie.setOverview(movieRequest.overview());
        movie.setPosterPath(movieRequest.posterPath());
        movie.setBackdropPath(movieRequest.backdropPath());
        movie.setRuntime(movieRequest.runtime());
        movie.setStatus(movieRequest.status());
        movie.setTagline(movieRequest.tagline());
        movie.setOriginalLanguage(movieRequest.originalLanguage());
        movie.setBudget(movieRequest.budget());
        movie.setRevenue(movieRequest.revenue());
        movie.setImdbId(movieRequest.imdbId());
        movie.setHomepage(movieRequest.homepage());
        movie.setAdult(movieRequest.adult());
        movie.setVideo(movieRequest.video());
        return movie;

    }

    public MovieRequest toMovieRequest (Movie movie) {

        return new MovieRequest(movie.getTitle(),
                movie.getOriginalTitle(),
                movie.getReleaseDate(),
                movie.getOverview(),
                movie.getPosterPath(),
                movie.getBackdropPath(),
                movie.getRuntime(),
                movie.getStatus(),
                movie.getTagline(),
                movie.getOriginalLanguage(),
                movie.getBudget(),
                movie.getRevenue(),
                movie.getImdbId(),
                movie.getHomepage(),
                movie.getAdult(),
                movie.getVideo());

    }

    public MovieResponse toResponse(Movie movie) {

        return new MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getReleaseDate(),
                movie.getVoteAverage(),
                movie.getRuntime(),
                movie.getPosterPath()
        );
    }

    public void updateEntity(MovieRequest movieRequest, Movie movie) {

        movie.setTitle(movieRequest.title());
        movie.setOriginalTitle(movieRequest.originalTitle());
        movie.setReleaseDate(movieRequest.releaseDate());
        movie.setOverview(movieRequest.overview());
        movie.setPosterPath(movieRequest.posterPath());
        movie.setBackdropPath(movieRequest.backdropPath());
        movie.setAdult(movieRequest.adult());
        movie.setVideo(movieRequest.video());
        movie.setBudget(movieRequest.budget());
        movie.setRevenue(movieRequest.revenue());
        movie.setRuntime(movieRequest.runtime());
        movie.setStatus(movieRequest.status());
        movie.setTagline(movieRequest.tagline());
        movie.setImdbId(movieRequest.imdbId());
        movie.setOriginalLanguage(movieRequest.originalLanguage());
        movie.setHomepage(movieRequest.homepage());
    }


    public List<MovieResponse> toMovieResponseList(List<Movie> movies) {

        List<MovieResponse> movieResponseList = new ArrayList<>();
        for (Movie movie : movies) {
            movieResponseList.add(toResponse(movie));

        }
        return movieResponseList;

    }

}
