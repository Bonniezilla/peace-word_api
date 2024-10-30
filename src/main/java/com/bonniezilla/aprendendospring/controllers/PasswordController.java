package com.bonniezilla.aprendendospring.controllers;

import com.bonniezilla.aprendendospring.entities.Password;
import com.bonniezilla.aprendendospring.repositories.PasswordRepository;
import com.bonniezilla.aprendendospring.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

        public UserDoesNotExistsException(Long id) {
            super("User with id " + String.valueOf(id) + " doesn't exists.");
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Password not found.")
    public class PasswordDoesNotExistsException extends RuntimeException {
        public PasswordDoesNotExistsException() {}

        public PasswordDoesNotExistsException(Long id) {
            super("Password with id " + String.valueOf(id) + " doesn't exists.");
        }
    }


    @GetMapping
    public List<Password> findALl(@PathVariable(value = "id") Long userId) {
        List<Password> result = passwordRepository.findByUserId(userId);

        return result;
    }

    @GetMapping(value = "/{category}")
    public List<Password> findAllByCategory(@PathVariable(value = "category") String category) {
        List<Password> result = passwordRepository.findByCategory(category);

        return result;
    }

    @PostMapping
    public ResponseEntity<Object> createPassword(@PathVariable(value = "id") Long userID, @RequestBody @Valid Password passwordReq) {
        Password password = userRepository.findById(userID).map(user -> {
            passwordReq.setUser(user);
            return passwordRepository.save(passwordReq);
        }).orElseThrow(() -> new UserDoesNotExistsException(userID));

        return ResponseEntity.status(HttpStatus.OK).body("Password saved.");
    }

    @DeleteMapping(value = "/{passwordID}")
    public Object deletePassword(@PathVariable(value = "passwordID") Long passwordID) {
        Optional<Password> password = passwordRepository.findById(passwordID);

        if(password.isEmpty()) {
            return new PasswordDoesNotExistsException(passwordID);
        }

        passwordRepository.deleteById(passwordID);
        return ResponseEntity.status(HttpStatus.OK).body("Password Deleted.");
    }
}
