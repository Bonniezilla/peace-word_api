package com.bonniezilla.aprendendospring.controllers;

import com.bonniezilla.aprendendospring.dtos.UserDTO;
import com.bonniezilla.aprendendospring.entities.User;
import com.bonniezilla.aprendendospring.repositories.UserRepository;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") Long id, @RequestBody @Valid UserDTO userDTO) {
        Optional<User> dbUser = userRepository.findById(id);
        if (dbUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        var userEntity = dbUser.get();

        BeanUtils.copyProperties(userDTO, userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(userEntity));
    }
}
