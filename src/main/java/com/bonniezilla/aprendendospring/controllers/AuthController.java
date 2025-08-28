package com.bonniezilla.aprendendospring.controllers;

import com.bonniezilla.aprendendospring.dtos.AuthRequestDTO;
import com.bonniezilla.aprendendospring.dtos.AuthResponseDTO;
import com.bonniezilla.aprendendospring.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthRequestDTO requestDTO) throws Exception {
        AuthResponseDTO response = authService.login(requestDTO);

        return ResponseEntity.ok(response);
    }
}
