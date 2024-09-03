package com.bonniezilla.aprendendospring.dtos;

import com.bonniezilla.aprendendospring.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PasswordDTO(@NotBlank String password, String category, String url, User user) {
}
