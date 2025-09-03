package com.bonniezilla.aprendendospring.dtos;

import java.time.LocalDateTime;

public record ErrorResponseDTO(int status, String error, String path, LocalDateTime timestamp) {
    public ErrorResponseDTO(int status, String error, String path) {
        this(status, error, path, LocalDateTime.now());
    }
}
