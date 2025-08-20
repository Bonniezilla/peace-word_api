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


    // Create user Method
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id){
        // Returning one user or throwing error

        User user = userService.findById(id);

        // Instancing the user founded by id to response
        UserResponseDTO response = new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );

        return ResponseEntity.ok(response);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") Long id, @RequestBody UserRequestDTO userDTO) {
        UserResponseDTO updatedUser = userService.updateUser(id, userDTO);

        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") Long id) {
        // Instancing the deleted user
        User deletedUser = userService.deleteUser(id);

        UserResponseDTO response = new UserResponseDTO(
                deletedUser.getId(),
                deletedUser.getEmail(),
                deletedUser.getUsername()
        );

        return ResponseEntity.status(HttpStatus.OK).body("User " + response.username() + " deleted. ID: " + response.id());
    }
}