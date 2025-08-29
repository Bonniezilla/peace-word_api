package com.bonniezilla.aprendendospring.entities;

import java.util.UUID;

public class UserTestFactory {

    // Prevent instantiating the factory
    private UserTestFactory() {}

    // Constructor to create user with id (ONLY FOR TESTS)
    public static User create(UUID id, String email, String username, String password) {
        return new User(id, email, username, password);
    }
}
