package com.bonniezilla.aprendendospring.services;


import com.bonniezilla.aprendendospring.dtos.UserLoginDTO;
import com.bonniezilla.aprendendospring.dtos.UserRegisterDTO;
import com.bonniezilla.aprendendospring.entities.Role;
import com.bonniezilla.aprendendospring.entities.User;
import com.bonniezilla.aprendendospring.exceptions.ResourceAlreadyExistsException;
import com.bonniezilla.aprendendospring.repositories.UserRepository;
import com.bonniezilla.aprendendospring.utils.PasswordValidator;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public User createUser(@Valid UserRegisterDTO data) {
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

        return userRepository.save(user);
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
//    // Update user data by id
//    public UserRegisterDTO updateUser(UUID id, @Valid UserLoginDTO data) {
//        User dbUser = userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not find"));
//
//        if (data.email()!=null) {
//            dbUser.setEmail(data.email());
//        }
//
//        if (data.password()!=null) {
//            dbUser.setUsername(data.password());
//        }
//
//        userRepository.save(dbUser);
//
//        return UserRegisterDTO.fromUser(dbUser);
//    }
//
//    // Delete user by id
//    public User deleteUser(UUID id) {
//        User dbUser = userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        userRepository.delete(dbUser);
//
//        return dbUser;
//    }
}
