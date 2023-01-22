package dev.rvincent.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record Movie(
        UUID movieId,

        @NotBlank(message = "movieName must not be empty")
        String movieName,

        @NotBlank(message = "actorName must not be empty")
        String actorName,

        @Positive(message = "rating value must be greater than zero")
        @NotNull(message = "rating must be defined")
        float rating,

        @NotBlank(message = "genre must not be empty")
        String genre
) {}
