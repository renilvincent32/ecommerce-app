package dev.rvincent.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String email) {
        super(String.format("User with the email %s not found", email));
    }
}
