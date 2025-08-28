package com.bonniezilla.aprendendospring.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRegisterDTO(@NotBlank(message = "Username cannot be empty") String username,
                              @Email(message = "Email should be valid") @NotBlank(message = "Email cannot be empty") String email,
                              @NotBlank(message = "Password cannot be empty") String password) {}
