package dev.rvincent;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rvincent.controller.MovieController;
import dev.rvincent.domain.Movie;
import dev.rvincent.service.MovieService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
@Disabled
public class MovieControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    void whenActorNameIsEmpty() throws Exception {
        Movie movie = new Movie(
                UUID.randomUUID(),
                "Bruce Willis",
                null,
                3.4f,
                "Action");
        ObjectMapper objectMapper = new ObjectMapper();
        String movieJson = objectMapper.writeValueAsString(movie);
        mockMvc.perform(post("/api/v1/movie").content(movieJson))
                .andExpect(status().isNotFound());
    }

}
