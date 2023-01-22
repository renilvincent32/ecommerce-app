package dev.rvincent;

import dev.rvincent.domain.Movie;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class MovieValidationTests {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreValid() {
        Movie movie = new Movie(
                UUID.randomUUID(),
                "Die Hard",
                "Bruce Willis",
                3.4f,
                "Action");
        Set<ConstraintViolation<Movie>> constraintViolations = validator.validate(movie);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    void whenActorNameIsEmpty() {
        Movie movie = new Movie(
                UUID.randomUUID(),
                "Bruce Willis",
                null,
                3.4f,
                "Action");
        Set<ConstraintViolation<Movie>> constraintViolations = validator.validate(movie);
        assertThat(constraintViolations).extracting(ConstraintViolation::getMessage).contains("actorName must not be empty");
    }

    @Test
    void whenMovieNameAndGenreIsEmpty() {
        Movie movie = new Movie(
                UUID.randomUUID(),
                null,
                "Bruce Willis",
                3.4f,
                null);
        Set<ConstraintViolation<Movie>> constraintViolations = validator.validate(movie);
        assertThat(constraintViolations).extracting(ConstraintViolation::getMessage).contains("movieName must not be empty", "genre must not be empty");
    }
}
