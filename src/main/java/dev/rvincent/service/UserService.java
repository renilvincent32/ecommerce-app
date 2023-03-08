package dev.rvincent.service;

import dev.rvincent.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User addUser(User user) {
        return user;
    }

    public User getUserByEmail(String email) {
        return null;
    }

    public Iterable<User> findAllUsers() {
        return null;
    }
}
