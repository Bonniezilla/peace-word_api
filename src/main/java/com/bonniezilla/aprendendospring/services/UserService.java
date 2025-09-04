package com.bonniezilla.aprendendospring.services;


import com.bonniezilla.aprendendospring.dtos.*;
import com.bonniezilla.aprendendospring.entities.Role;
import com.bonniezilla.aprendendospring.entities.User;
import com.bonniezilla.aprendendospring.exceptions.ResourceAlreadyExistsException;
import com.bonniezilla.aprendendospring.repositories.UserRepository;
import com.bonniezilla.aprendendospring.utils.PasswordValidator;
import jakarta.validation.Valid;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    // Instantiating userRepository
    private final UserRepository userRepository;

    // Instantiating passwordEncoder
    private final PasswordEncoder passwordEncoder;

    // Instantiating jwtService
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // Create user function
    public UserCreatedDTO createUser(@Valid UserRegisterDTO data) {
        PasswordValidator.validatePassword(data.password());

        // Encoding raw password
        String encodedPassword = passwordEncoder.encode(data.password());

        User user = new User();

        user.setEmail(data.email());
        user.setPassword(encodedPassword);
        user.setUsername(data.username());
        user.setRole(Role.valueOf("ROLE_USER"));

        // Verify if exists a user with data.email() or data.username()
        if (userRepository.existsByEmail(data.email())) {
            throw new ResourceAlreadyExistsException("User with email " + data.email() + " already exists");
        }
        if (userRepository.existsByUsername(data.username())) {
            throw new ResourceAlreadyExistsException("User with username " + data.username() + " already exists");
        }

        User userCreated = userRepository.save(user);

        return new UserCreatedDTO(userCreated.getId(), "User created succesfully!");
    }

    // Find all users functions (ONLY FOR ADMINS)
    public List<UserCompleteDataDTO> findAll() {
        return userRepository.findAll().stream()
                .map(UserCompleteDataDTO::fromEntity)
                .toList();
    }

    // Find a user by his id (ONLY FOR ADMINS)
    public UserCompleteDataDTO findById(UUID id) {
        // Return user or throw exception
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found by id!"));

        return UserCompleteDataDTO.fromEntity(user);
    }

    // Get user data by his token
    public UserDataDTO getUserData(String username) {
        // Return user or throw exception
        User user  = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not find!"));

        return UserDataDTO.fromEntity(user);
    }

    // Update user data
    public UserUpdatedDTO updateUser(@Valid UpdateUserDTO data, String username) {
        User dbUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not find!"));

        if (!dbUser.getUsername().equals(username)){
            throw new RuntimeException("You cannot update other user!");
        }

        if (!passwordEncoder.matches(data.currentPassword(), dbUser.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }


        if (data.email() != null && !data.email().isBlank()) {
            dbUser.setEmail(data.email());
        }

        if (data.username() != null && !data.username().isBlank()) {
            dbUser.setUsername(data.newPassword());
        }

        PasswordValidator.isStrongPassword(data.newPassword());

        if (data.newPassword() != null && !data.newPassword().isBlank()) {
            dbUser.setPassword(passwordEncoder.encode(data.newPassword()));
        }

        userRepository.save(dbUser);

        return new UserUpdatedDTO(dbUser.getId(), "User updated successfully!");
    }

    // Delete user by his token
    public UserDeletedDTO deleteUser(String username, String password) {
        User dbUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!passwordEncoder.matches(password, dbUser.getPassword())){
            throw new BadCredentialsException("Incorrect password!");
        }

        userRepository.delete(dbUser);

        return UserDeletedDTO.fromEntity(dbUser, "User deleted successfully!");
    }
}
