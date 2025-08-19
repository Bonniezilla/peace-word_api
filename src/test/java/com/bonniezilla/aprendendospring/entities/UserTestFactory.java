package com.bonniezilla.aprendendospring.entities;

public class UserTestFactory {

    // Prevent instantiating the factory
    private UserTestFactory() {}

    // Constructor to create user with id (ONLY FOR TESTS)
    public static User create(Long id, String email, String username) {
        return new User(id, email, username);
    }
}
