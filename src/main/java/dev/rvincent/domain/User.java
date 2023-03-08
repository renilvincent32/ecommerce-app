package dev.rvincent.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record User(

        @NotBlank(message = "firstName must not be empty")
        String firstName,

        String lastName,

        @NotBlank(message = "phoneNumber must not be empty")
        String phoneNumber,

        @NotBlank(message = "password must not be empty")
        String password,

        @NotBlank(message = "email must not be empty")
        String email
) {}
