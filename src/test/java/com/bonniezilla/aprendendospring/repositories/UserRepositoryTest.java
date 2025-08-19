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


}