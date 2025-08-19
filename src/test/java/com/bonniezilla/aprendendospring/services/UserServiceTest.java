package com.bonniezilla.aprendendospring.services;

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

        Assertions.assertEquals(user, response);
        Assertions.assertEquals(user.getId(), response.getId());
        Assertions.assertEquals(user.getEmail(), response.getEmail());
        Assertions.assertEquals(user.getUsername(), response.getUsername());

        Mockito.verify(userRepository).findById(999L);
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }
}