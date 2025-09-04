package com.bonniezilla.aprendendospring.dtos;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserDTO(String username, String email, @NotBlank(message = "Password cannot be empty!") String currentPassword, String newPassword) {
}
