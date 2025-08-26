package com.bonniezilla.aprendendospring.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(@NotBlank String email, @NotBlank String password) {
}
