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
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(@Valid UserRequestDTO data) {
        User newUser = new User(data.email(), data.username());

        return userRepository.save(newUser);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        // Return user or throw exception
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    public User updateUser(Long id, @Valid UserRequestDTO data) {
        User dbUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not find"));

        if (data.email()!=null) {
            dbUser.setEmail(data.email());
        }

        if (data.username()!=null) {
            dbUser.setUsername(data.username());
        }

        return userRepository.save(dbUser);
    }

    public UserResponseDTO deleteUser(Long id) {
        User dbUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponseDTO username = new UserResponseDTO(
                dbUser.getId(),
                dbUser.getUsername(),
                dbUser.getEmail()
        );

        userRepository.delete(dbUser);

        return username;
    }
}
