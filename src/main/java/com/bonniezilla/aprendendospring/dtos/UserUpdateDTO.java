package com.bonniezilla.aprendendospring.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateDTO(String username, String email, @NotBlank(message = "Password cannot be empty!") String currentPassword, String newPassword) {
}
