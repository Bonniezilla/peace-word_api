package com.bonniezilla.aprendendospring.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserDTO(@NotBlank String email, @NotBlank String username) {
}
