package dev.rvincent;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rvincent.controller.UserController;
import dev.rvincent.domain.User;
import dev.rvincent.service.UserService;
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

@WebMvcTest(UserController.class)
@Disabled
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    void whenLastNameIsEmpty() throws Exception {
        User user = new User(
                "Bruce Willis",
                "",
                "12345",
                "password",
                "bruce.willis@gmail.com");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/api/v1/user").content(userJson))
                .andExpect(status().isNotFound());
    }

}
