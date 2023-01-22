package dev.rvincent.exception;

public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException(String movieName) {
        super(String.format("Movie with the name %s not found", movieName));
    }
}
