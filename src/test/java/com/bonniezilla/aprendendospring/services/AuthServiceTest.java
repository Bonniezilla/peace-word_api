package com.bonniezilla.aprendendospring.services;

import com.bonniezilla.aprendendospring.dtos.AuthRequestDTO;
import com.bonniezilla.aprendendospring.dtos.AuthResponseDTO;
import com.bonniezilla.aprendendospring.entities.User;
import com.bonniezilla.aprendendospring.entities.UserTestFactory;
import com.bonniezilla.aprendendospring.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setup () {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loginShouldReturnAuthResponseDTO() throws Exception {
        // Arrange
        AuthRequestDTO requestDTO = new AuthRequestDTO("test@user.com", "StrongPassword123@");
        User mockUser = UserTestFactory.create(UUID.fromString("00000000-0000-0000-0000-000000000001"), "test@user.com", "user-test", "StrongPassword123@");

        Authentication authentication = new UsernamePasswordAuthenticationToken(mockUser, null);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(jwtService.generateToken(mockUser)).thenReturn("fake-jwt-token");

        // Act
        AuthResponseDTO response = authService.login(requestDTO);

        // Assert
        assertNotNull(response);
        assertEquals("fake-jwt-token", response.token());

        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generateToken(mockUser);

    }
}
