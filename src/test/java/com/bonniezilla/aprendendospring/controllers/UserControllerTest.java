package com.bonniezilla.aprendendospring.controllers;

import com.bonniezilla.aprendendospring.dtos.UserCreatedDTO;
import com.bonniezilla.aprendendospring.dtos.UserRegisterDTO;
import com.bonniezilla.aprendendospring.dtos.UserUpdateDTO;
import com.bonniezilla.aprendendospring.dtos.UserUpdatedDTO;
import com.bonniezilla.aprendendospring.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveUserSuccessCase() throws Exception {
        UserRegisterDTO registerDTO = new UserRegisterDTO("user-test", "test@email.com", "Password123@");
        UserCreatedDTO response = new UserCreatedDTO(UUID.fromString("00000000-0000-0000-0000-000000000001"), "User created successfully!");


        Mockito.when(userService.createUser(registerDTO)).thenReturn(response);

        mockMvc.perform((RequestBuilder) post("/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(response.id().toString()))
                .andExpect(jsonPath("$.message").value(response.message()));
    }

    @Test
    @WithMockUser(username = "old@email.com")
    void updateUserSuccessCase() throws Exception {
        // Arrange
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UserUpdateDTO data = new UserUpdateDTO("username", "test@email", "Password1@", "newPassword1@");

        UserUpdatedDTO response = new UserUpdatedDTO(userId, "User updated successfully!");

        Mockito.when(userService.updateUser(Mockito.any(UserUpdateDTO.class), Mockito.eq("old@email.com"))).thenReturn(response);

        // Act + Assert
        mockMvc.perform(patch("/users/me")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.message").value("User updated successfully!"));
    }
}