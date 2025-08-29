package com.bonniezilla.aprendendospring.dtos;

import java.util.UUID;

public record UserCreateDTO(UUID id, String message) {
}
