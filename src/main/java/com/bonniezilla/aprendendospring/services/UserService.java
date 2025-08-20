package com.bonniezilla.aprendendospring.services;


import com.bonniezilla.aprendendospring.dtos.UserRequestDTO;
import com.bonniezilla.aprendendospring.dtos.UserResponseDTO;
import com.bonniezilla.aprendendospring.entities.User;
import com.bonniezilla.aprendendospring.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    // Instancing userRepository
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create user function
    public UserResponseDTO createUser(@Valid UserRequestDTO data) {
        User newUserData = new User(data);

        User newUser = userRepository.save(newUserData);

        return UserResponseDTO.fromUser(newUser);
    }

    // Find all users function
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Find a user by his id
    public User findById(Long id) {
        // Return user or throw exception
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    // Update user data by id
    public UserResponseDTO updateUser(Long id, @Valid UserRequestDTO data) {
        User dbUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not find"));

        if (data.email()!=null) {
            dbUser.setEmail(data.email());
        }

        if (data.username()!=null) {
            dbUser.setUsername(data.username());
        }

        userRepository.save(dbUser);

        return UserResponseDTO.fromUser(dbUser);
    }

    // Delete user by id
    public User deleteUser(Long id) {
        User dbUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(dbUser);

        return dbUser;
    }
}
