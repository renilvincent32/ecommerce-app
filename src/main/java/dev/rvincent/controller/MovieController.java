package dev.rvincent.controller;

import dev.rvincent.domain.Movie;
import dev.rvincent.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Movie addMovie(@Valid @RequestBody Movie movie) {
        return movieService.addMovie(movie);
    }

    @GetMapping("{movieName}")
    public Movie getMovieByName(@PathVariable String movieName) {
        return movieService.getMovieByName(movieName);
    }

    @GetMapping
    public Iterable<Movie> getAllMovies() {
        return movieService.findAllMovies();
    }
}
