package dev.rvincent;

import dev.rvincent.domain.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UserValidationTests {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreValid() {
        User user = new User(
                "Die Hard",
                "Bruce Willis",
                "12345",
                "Action", "bruce.willis@gmail.com");
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    void whenFirstNameIsEmpty() {
        User user = new User(
                "",
                "Willis",
                "12345",
                "Action", "bruce.willis@gmail.com");
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations).extracting(ConstraintViolation::getMessage).contains("firstName must not be empty");
    }

    @Test
    void whenFirstNameAndEmailIsEmpty() {
        User user = new User(
                "",
                "Bruce Willis",
                "123454",
                null,"");
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        assertThat(constraintViolations).extracting(ConstraintViolation::getMessage).contains("firstName must not be empty", "email must not be empty");
    }
}
