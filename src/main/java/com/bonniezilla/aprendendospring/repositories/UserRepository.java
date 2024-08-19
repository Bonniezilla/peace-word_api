package com.bonniezilla.aprendendospring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bonniezilla.aprendendospring.entities.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
