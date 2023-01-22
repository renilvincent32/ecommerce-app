package dev.rvincent.service;

import dev.rvincent.domain.Movie;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    public Movie addMovie(Movie movie) {
        return movie;
    }

    public Movie getMovieByName(String movieName) {
        return null;
    }

    public Iterable<Movie> findAllMovies() {
        return null;
    }
}
