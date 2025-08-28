package com.bonniezilla.aprendendospring.controllers;

import com.bonniezilla.aprendendospring.dtos.AuthRequestDTO;
import com.bonniezilla.aprendendospring.dtos.AuthResponseDTO;
import com.bonniezilla.aprendendospring.services.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthController.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void loginSuccessCase() throws Exception {
        // Arrange
        AuthRequestDTO requestDTO = new AuthRequestDTO("test@email", "StrongPasswords123@");
        AuthResponseDTO responseDTO = new AuthResponseDTO("fake-jwt-token");

        // Mocking the AuthService
        Mockito.when(authService.login(requestDTO)).thenReturn(responseDTO);

        mockMvc.perform((RequestBuilder) post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(responseDTO.token()));
    }
}