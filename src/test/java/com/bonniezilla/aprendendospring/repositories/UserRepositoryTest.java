package com.bonniezilla.aprendendospring.repositories;

import com.bonniezilla.aprendendospring.dtos.UserRequestDTO;
import com.bonniezilla.aprendendospring.entities.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {


    @Autowired
    EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should get user by id successfully from DB")
    void findUserByIdSuccess() {
        UserRequestDTO data = new UserRequestDTO("user@test.com","UserTest");

        User newUser = this.createUser(data);

        Optional<User> response = this.userRepository.findById(newUser.getId());

        assertThat(response.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Shouldn't get user if don't exist")
    void findUserByIdFail() {
        UserRequestDTO data = new UserRequestDTO("user@test.com","UserTest");

        Optional<User> response = this.userRepository.findById(0L);

        assertThat(response.isEmpty()).isTrue();
    }

    private User createUser(UserRequestDTO data) {
        User newUser = new User();

        newUser.setUsername(data.username());
        newUser.setEmail(data.email());

        this.entityManager.persist(newUser);

        return newUser;
    }
}