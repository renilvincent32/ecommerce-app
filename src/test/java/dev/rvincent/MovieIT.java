package dev.rvincent;

import dev.rvincent.domain.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenAddMovie() {
        Movie movie = new Movie(
                UUID.randomUUID(),
                "Die Hard",
                "Bruce Willis",
                3.4f,
                "Action");

        webTestClient
                .post()
                .uri("/api/v1/movie")
                .bodyValue(movie)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Movie.class).value(createdMovie -> {
                    assertThat(createdMovie).isNotNull();
                    assertThat(createdMovie.movieName()).isEqualTo(movie.movieName());
                    assertThat(createdMovie.actorName()).isEqualTo(movie.actorName());
                    assertThat(createdMovie.genre()).isEqualTo(movie.genre());
                    assertThat(createdMovie.rating()).isEqualTo(movie.rating());
                });
    }
}
