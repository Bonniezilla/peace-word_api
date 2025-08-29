package com.bonniezilla.aprendendospring.services;

import com.bonniezilla.aprendendospring.dtos.UserCreateDTO;
import com.bonniezilla.aprendendospring.dtos.UserRegisterDTO;
import com.bonniezilla.aprendendospring.entities.User;
import com.bonniezilla.aprendendospring.entities.UserTestFactory;
import com.bonniezilla.aprendendospring.exceptions.ResourceAlreadyExistsException;
import com.bonniezilla.aprendendospring.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createUserSuccessCase() {
        UserRegisterDTO dto = new UserRegisterDTO("username", "test@example", "Password1@");
        String encodedPassword = "encodedPassword";

        User savedUser = UserTestFactory.create(
                UUID.fromString("00000000-0000-0000-0000-000000000001"),
                dto.username(),
                dto.email(),
                encodedPassword
        );

        when(userRepository.existsByUsername(dto.username())).thenReturn(false);
        when(userRepository.existsByEmail(dto.email())).thenReturn(false);
        when(passwordEncoder.encode(dto.password())).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            return UserTestFactory.create(
                    UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    user.getUsername(),
                    user.getEmail(),
                    encodedPassword
            );
        });

        UserCreateDTO response = userService.createUser(dto);

        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000001"), response.id());
        assertEquals("User created succesfully!", response.message());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUserFailByExistentEmailCase() {
        UserRegisterDTO dto = new UserRegisterDTO("username", "test@example", "Password1@");
        String encodedPassword = "encodedPassword";

        when(userRepository.existsByUsername(dto.username())).thenReturn(false);
        when(userRepository.existsByEmail(dto.email())).thenReturn(true);
        when(passwordEncoder.encode(dto.password())).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResourceAlreadyExistsException existsException = assertThrows(
                ResourceAlreadyExistsException.class, () ->  userService.createUser(dto)
        );

        assertEquals("User with email test@example already exists", existsException.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void createUserFailByExistentUsernameCase() {
        UserRegisterDTO dto = new UserRegisterDTO("username", "test@example", "Password1@");
        String encodedPassword = "encodedPassword";

        when(userRepository.existsByUsername(dto.username())).thenReturn(true);
        when(userRepository.existsByEmail(dto.email())).thenReturn(false);
        when(passwordEncoder.encode(dto.password())).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResourceAlreadyExistsException existsException = assertThrows(
                ResourceAlreadyExistsException.class, () ->  userService.createUser(dto)
        );

        assertEquals("User with username username already exists", existsException.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }
//        @Test
//        void findAll() {
//        }
//
//        @Test
//        void findById() {
//            User user = UserTestFactory.create(UUID.fromString("test"), "test@user.com", "UserTest");
//
//            when(userRepository.findById(UUID.fromString("test"))).thenReturn(Optional.of(user));
//
//            User response = userService.findById(UUID.fromString("test"));
//
//            assertEquals(user, response);
//            assertEquals(user.getId(), response.getId());
//            assertEquals(user.getEmail(), response.getEmail());
//            assertEquals(user.getUsername(), response.getUsername());
//
//            Mockito.verify(userRepository).findById(UUID.fromString("test"));
//        }
//
//        @Test
//        void updateUser() {
//            User user = UserTestFactory.create(UUID.fromString("test"), "test@user.com", "UserTest");
//
//            when(userRepository.findById(UUID.fromString("test"))).thenReturn(Optional.of(user));
//
//            when(userRepository.save(Mockito.any(User.class))).thenAnswer((invocation -> invocation.getArgument(0)));
//
//            UserLoginDTO requestDTO = new UserLoginDTO("test@updated.com", "Updated test");
//
//            UserRegisterDTO result = userService.updateUser(UUID.fromString("test"), requestDTO);
//
//            assertNotNull(result);
//            assertEquals(999L, result.id());
//            assertEquals(requestDTO.password(), result.username());
//            assertEquals(requestDTO.email(), result.email());
//
//            Mockito.verify(userRepository).save(Mockito.any(User.class));
//        }
//
//        @Test
//        void deleteUser() {
//        }
//    }
}

