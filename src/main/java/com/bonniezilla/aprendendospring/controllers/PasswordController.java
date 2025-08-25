package com.bonniezilla.aprendendospring.controllers;

import com.bonniezilla.aprendendospring.entities.Password;
import com.bonniezilla.aprendendospring.repositories.PasswordRepository;
import com.bonniezilla.aprendendospring.repositories.UserRepository;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/users/{id}/passwords")
public class PasswordController {
    @Autowired
    private PasswordRepository passwordRepository;

    @Autowired
    private UserRepository userRepository;

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User not found.")
    public class UserDoesNotExistsException extends RuntimeException {
        public UserDoesNotExistsException() {}

        public UserDoesNotExistsException(UUID id) {
            super("User with id " + String.valueOf(id) + " doesn't exists.");
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Password not found.")
    public class PasswordDoesNotExistsException extends RuntimeException {
        public PasswordDoesNotExistsException() {}

        public PasswordDoesNotExistsException(UUID id) {
            super("Password with id " + String.valueOf(id) + " doesn't exists.");
        }
    }


    @GetMapping
    public List<Password> findALl(@PathVariable(value = "id") UUID userId) {
        List<Password> result = passwordRepository.findByUserId(userId);

        return result;
    }

    @GetMapping(value = "/byCategory/{category}")
    public List<Password> findAllByCategory(@PathVariable(value = "category") String category) {
        List<Password> result = passwordRepository.findByCategory(category);

        return result;
    }

    @GetMapping(value = "/byId/{passwordId}")
    public ResponseEntity<Object> findById(@PathVariable(value = "passwordId") UUID passwordID) {
        Optional<Password> result = passwordRepository.findById(passwordID);

        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PasswordDoesNotExistsException(passwordID));
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping
    public ResponseEntity<Object> createPassword(@PathVariable(value = "id") UUID userID, @RequestBody @Valid Password passwordReq) {
        userRepository.findById(userID).map(user -> {
            passwordReq.setUser(user);
            return passwordRepository.save(passwordReq);
        }).orElseThrow(() -> new UserDoesNotExistsException(userID));

        Map<String, String> response = new HashMap<>();
        response.put("message", "Password saved.");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping(value = "/byId/{passwordID}")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "passwordID") UUID passwordID, @RequestBody Optional<Password> passwordReq) {
        if(passwordReq.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request is empty.");
        }

        Optional<Password> dbPassword = passwordRepository.findById(passwordID);
        if (dbPassword.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PasswordDoesNotExistsException(passwordID));
        }

        Password passwordEntity = dbPassword.get();
        if (passwordReq.get().getPassword() != null) {
            passwordEntity.setPassword(passwordReq.get().getPassword());
        }
        if (passwordReq.get().getCategory() != null) {
            passwordEntity.setCategory(passwordReq.get().getCategory());
        }
        if (passwordReq.get().getUrl() != null) {
            passwordEntity.setUrl(passwordReq.get().getUrl());
        }

        passwordRepository.save(passwordEntity);
        return ResponseEntity.status(HttpStatus.OK).body("Password updated.");
    }

    @DeleteMapping(value = "/{passwordID}")
    public ResponseEntity<Object> deletePassword(@PathVariable(value = "passwordID") UUID passwordID) {
        Optional<Password> password = passwordRepository.findById(passwordID);

        if(password.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PasswordDoesNotExistsException(passwordID));
        }

        passwordRepository.deleteById(passwordID);
        return ResponseEntity.status(HttpStatus.OK).body("Password Deleted.");
    }
}
