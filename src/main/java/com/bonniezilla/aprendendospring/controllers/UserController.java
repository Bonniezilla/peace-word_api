package com.bonniezilla.aprendendospring.controllers;

import com.bonniezilla.aprendendospring.dtos.UserDTO;
import com.bonniezilla.aprendendospring.entities.User;
import com.bonniezilla.aprendendospring.repositories.UserRepository;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserDTO userDTO) {
        var userEntity = new User();
        BeanUtils.copyProperties(userDTO, userEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(userEntity));
    }

    @GetMapping
    public List<User> findAll() {
        List<User> result = userRepository.findAll();

        return result;
    }

    @GetMapping(value = "/{id}")
    public User findById(@PathVariable Long id){
        User result = userRepository.findById(id).get();

        return result;
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") Long id, @RequestBody Optional<UserDTO> userDTO) {
        if(userDTO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request are empty.");
        }

        Optional<User> dbUser = userRepository.findById(id);
        if (dbUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        if (userDTO.get().email()!=null) {
            dbUser.get().setEmail(userDTO.get().email());
        }

        if (userDTO.get().username()!=null) {
            dbUser.get().setUsername(userDTO.get().username());
        }

        var userEntity = dbUser.get();
        userRepository.save(userEntity);

        return ResponseEntity.status(HttpStatus.OK).body(userEntity);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") Long id) {
        Optional<User> dbUser = userRepository.findById(id);

        if(dbUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        var userEntity = dbUser.get();

        userRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body("User " + userEntity.getUsername() + " deleted.");
    }
}