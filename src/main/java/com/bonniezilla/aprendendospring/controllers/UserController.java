package com.bonniezilla.aprendendospring.controllers;

import com.bonniezilla.aprendendospring.dtos.UserCreateDTO;
import com.bonniezilla.aprendendospring.dtos.UserRegisterDTO;
import com.bonniezilla.aprendendospring.entities.User;

import com.bonniezilla.aprendendospring.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/create")
    public ResponseEntity<UserCreateDTO> saveUser(@RequestBody @Valid UserRegisterDTO userRegisterData) {
        UserCreateDTO savedUser = userService.createUser(userRegisterData);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

//    // Get all users method
//    @GetMapping
//    public ResponseEntity<List<User>> findAll() {
//        // Returning list of all users
//
//        return ResponseEntity.ok(userService.findAll());
//    }
//
//    // Get one user by id method
//    @GetMapping(value = "/{id}")
//    public ResponseEntity<UserRegisterDTO> findById(@PathVariable UUID id){
//        // Returning one user
//        User user = userService.findById(id);
//
//        // Instantiating UserResponseDTO
//        UserRegisterDTO response = UserRegisterDTO.fromUser(user);
//
//        return ResponseEntity.ok(response);
//    }
//
//    // Update one user by id method
//    @PatchMapping(value = "/{id}")
//    public ResponseEntity<UserRegisterDTO> updateUser(@PathVariable(value = "id") UUID id, @RequestBody UserLoginDTO userDTO) {
//        UserRegisterDTO updatedUser = userService.updateUser(id, userDTO);
//
//        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
//    }
//
//    // Delete one user by id method
//    @DeleteMapping(value = "/{id}")
//    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID id) {
//        // Deleting and instantiating the deleted user
//        User deletedUser = userService.deleteUser(id);
//
//        // Instantiating UserResponseDTO
//        UserRegisterDTO response = UserRegisterDTO.fromUser(deletedUser);
//
//        return ResponseEntity.status(HttpStatus.OK).body("User " + response.username() + " deleted.");
//    }
}