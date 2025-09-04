package com.bonniezilla.aprendendospring.dtos;

import com.bonniezilla.aprendendospring.entities.User;

import java.util.UUID;

public record UserDeletedDTO(UUID id, String username, String message) {
    public static UserDeletedDTO fromEntity(User user, String message) {
        return new UserDeletedDTO(user.getId(), user.getUsername(), message);
    }
}
