package com.bonniezilla.aprendendospring.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(@NotBlank String email, @NotBlank String username) {
}
