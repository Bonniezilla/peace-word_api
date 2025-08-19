package com.bonniezilla.aprendendospring.repositories;

import com.bonniezilla.aprendendospring.dtos.UserRequestDTO;
import com.bonniezilla.aprendendospring.dtos.UserResponseDTO;
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
        // Instancing a "client request"
        UserRequestDTO data = new UserRequestDTO("user@test.com","UserTest");

        // Requesting repository to create a test user
        User newUser = this.createUser(data);

        // Try to get user by ID
        Optional<User> response = this.userRepository.findById(newUser.getId());

        // Test if user exists
        assertThat(response.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Shouldn't get user if don't exist")
    void findUserByIdFail() {
        // Instancing a "client request"
        UserRequestDTO data = new UserRequestDTO("user@test.com","UserTest");

        // Try to find a user with an impossible ID
        Optional<User> response = this.userRepository.findById(0L);

        // Test if user with a not existent id can be found
        assertThat(response.isEmpty()).as("Expect to don't find any user.").isTrue();

    }

    @Test
    @DisplayName("Should delete user with success by his ID.")
    void deleteUserSuccess() {
        // Instancing a "client request"
        UserRequestDTO data = new UserRequestDTO("user@test.com","UserTest");

        // Requesting repository to create a test user
        User newUser = this.createUser(data);

        // Try to get user by ID
        User dbUser = this.userRepository.findById(newUser.getId())
                .orElseThrow(RuntimeException::new);

        // Try to delete user founded
        this.userRepository.delete(dbUser);

        // Search deleted user by his ID
        boolean exists = this.userRepository.existsById(dbUser.getId());

        // Test if user exists after deleting him.
        assertThat(exists).as("Expect to don't find user after trying to delete him. ").isFalse();

    }

    private User createUser(UserRequestDTO data) {
        User newUser = new User();

        newUser.setUsername(data.username());
        newUser.setEmail(data.email());

        this.entityManager.persist(newUser);

        return newUser;
    }
}