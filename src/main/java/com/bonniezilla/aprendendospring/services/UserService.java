package com.bonniezilla.aprendendospring.services;


import com.bonniezilla.aprendendospring.dtos.UserCreatedDTO;
import com.bonniezilla.aprendendospring.dtos.UserRegisterDTO;
import com.bonniezilla.aprendendospring.dtos.UserUpdateDTO;
import com.bonniezilla.aprendendospring.dtos.UserUpdatedDTO;
import com.bonniezilla.aprendendospring.entities.Role;
import com.bonniezilla.aprendendospring.entities.User;
import com.bonniezilla.aprendendospring.exceptions.ResourceAlreadyExistsException;
import com.bonniezilla.aprendendospring.repositories.UserRepository;
import com.bonniezilla.aprendendospring.utils.PasswordValidator;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    // Instancing userRepository
    private final UserRepository userRepository;

    // Instantiating passwordEncoder
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

//    // Find all users function
//    public List<User> findAll() {
//        return userRepository.findAll();
//    }
//
    // Find a user by his id
    public User findByEmail(String email) {
        // Return user or throw exception
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found by email!"));
    }
//
    // Update user data
    public UserUpdatedDTO updateUser(@Valid UserUpdateDTO data, String autheticatedEmail) {
        User dbUser = userRepository.findByEmail(autheticatedEmail)
                .orElseThrow(() -> new RuntimeException("User not find"));

        if (!dbUser.getEmail().equals(autheticatedEmail)){
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

//    // Delete user by id
//    public User deleteUser(UUID id) {
//        User dbUser = userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        userRepository.delete(dbUser);
//
//        return dbUser;
//    }
}
