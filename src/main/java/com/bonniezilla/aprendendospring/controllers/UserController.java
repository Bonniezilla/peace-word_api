package com.bonniezilla.aprendendospring.controllers;

import com.bonniezilla.aprendendospring.dtos.*;

import com.bonniezilla.aprendendospring.entities.User;
import com.bonniezilla.aprendendospring.services.JwtService;
import com.bonniezilla.aprendendospring.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

//    // Logic to check admin privileges
//    private void checkAdmin(String token) {
//        List<String> roles = jwtService.getRolesFromToken(token);
//        System.out.println(roles);
//        if (!roles.contains("ADMIN")) {
//            throw new AccessDeniedException("You need to be admin to realize that operation!");
//        }
//    }


    // Create user method
    @PostMapping("/create")
    public ResponseEntity<UserCreatedDTO> saveUser(@RequestBody @Valid UserRegisterDTO userRegisterData) {
        UserCreatedDTO savedUser = userService.createUser(userRegisterData);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    // Get all users methods
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDataDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    // Get one user by id method
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDataDTO> findById(@PathVariable UUID id){
        // Returning one user
        UserDataDTO response = userService.findById(id);;

        return ResponseEntity.ok(response);
    }

    // Update one user by id method
    @PatchMapping("/me")
    public ResponseEntity<UserUpdatedDTO> updateUser(@RequestBody UserUpdateDTO userDTO, @AuthenticationPrincipal String username) {
        UserUpdatedDTO response = userService.updateUser(userDTO, username);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
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