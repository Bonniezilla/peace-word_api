package com.bonniezilla.aprendendospring.services;

import com.bonniezilla.aprendendospring.dtos.AuthRequestDTO;
import com.bonniezilla.aprendendospring.dtos.AuthResponseDTO;
import com.bonniezilla.aprendendospring.entities.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponseDTO login(AuthRequestDTO request) throws Exception {
        // Try to auth the user
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        // If passed of code above, user it's auth
        User user = (User) auth.getPrincipal();

        String token = jwtService.generateToken(user);

        return new AuthResponseDTO(token, user.getEmail(), user.getUsername());
    }
}
