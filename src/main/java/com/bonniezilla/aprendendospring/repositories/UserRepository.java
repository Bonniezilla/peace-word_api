package com.bonniezilla.aprendendospring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bonniezilla.aprendendospring.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
