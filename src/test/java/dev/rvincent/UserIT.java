package dev.rvincent;

import dev.rvincent.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenAddUser() {
        User user = new User(
                "Die Hard",
                "Bruce Willis",
                "12345",
                "Action", "bruce.willis@gmail.com");

        webTestClient
                .post()
                .uri("/api/v1/user")
                .bodyValue(user)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(User.class).value(createdUser -> {
                    assertThat(createdUser).isNotNull();
                    assertThat(createdUser.firstName()).isEqualTo(user.firstName());
                    assertThat(createdUser.lastName()).isEqualTo(user.lastName());
                    assertThat(createdUser.email()).isEqualTo(user.email());
                    assertThat(createdUser.phoneNumber()).isEqualTo(user.phoneNumber());
                });
    }
}
