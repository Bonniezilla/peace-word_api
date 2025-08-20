package com.bonniezilla.aprendendospring.services;

import com.bonniezilla.aprendendospring.dtos.UserRequestDTO;
import com.bonniezilla.aprendendospring.dtos.UserResponseDTO;
import com.bonniezilla.aprendendospring.entities.User;
import com.bonniezilla.aprendendospring.entities.UserTestFactory;
import com.bonniezilla.aprendendospring.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createUser() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
        User user = UserTestFactory.create(999L, "test@user.com", "UserTest");

        Mockito.when(userRepository.findById(999L)).thenReturn(Optional.of(user));

        User response = userService.findById(999L);

        assertEquals(user, response);
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getEmail(), response.getEmail());
        assertEquals(user.getUsername(), response.getUsername());

        Mockito.verify(userRepository).findById(999L);
    }

    @Test
    void updateUser() {
        User user = UserTestFactory.create(999L, "test@user.com", "UserTest");

        Mockito.when(userRepository.findById(999L)).thenReturn(Optional.of(user));

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenAnswer((invocation -> invocation.getArgument(0)));

        UserRequestDTO requestDTO = new UserRequestDTO("test@updated.com", "Updated test");

        UserResponseDTO result = userService.updateUser(999L, requestDTO);

        assertNotNull(result);
        assertEquals(999L, result.id());
        assertEquals(requestDTO.username(), result.username());
        assertEquals(requestDTO.email(), result.email());

        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    void deleteUser() {
    }
}