package com.bonniezilla.aprendendospring.controllers;

import com.bonniezilla.aprendendospring.dtos.UserRequestDTO;
import com.bonniezilla.aprendendospring.dtos.UserResponseDTO;
import com.bonniezilla.aprendendospring.entities.User;

import com.bonniezilla.aprendendospring.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UserController {

    // Declaring userService
    private final UserService userService;

    // Getting the value to userService
    private UserController (UserService userService) {
        this.userService = userService;
    }


    // Create user method
    @PostMapping
    public ResponseEntity<UserResponseDTO> saveUser(@RequestBody @Valid UserRequestDTO userDTO) {
         UserResponseDTO savedUser = userService.createUser(userDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    // Get all users method
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        // Returning list of all users

        return ResponseEntity.ok(userService.findAll());
    }

    // Get one user by id method
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable UUID id){
        // Returning one user
        User user = userService.findById(id);

        // Instantiating UserResponseDTO
        UserResponseDTO response = UserResponseDTO.fromUser(user);

        return ResponseEntity.ok(response);
    }

    // Update one user by id method
    @PatchMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable(value = "id") UUID id, @RequestBody UserRequestDTO userDTO) {
        UserResponseDTO updatedUser = userService.updateUser(id, userDTO);

        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    // Delete one user by id method
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID id) {
        // Deleting and instantiating the deleted user
        User deletedUser = userService.deleteUser(id);

        // Instantiating UserResponseDTO
        UserResponseDTO response = UserResponseDTO.fromUser(deletedUser);

        return ResponseEntity.status(HttpStatus.OK).body("User " + response.username() + " deleted. ID: " + response.id());
    }
}