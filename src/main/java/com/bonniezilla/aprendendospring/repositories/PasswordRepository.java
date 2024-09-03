package com.bonniezilla.aprendendospring.repositories;

import com.bonniezilla.aprendendospring.entities.Password;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasswordRepository extends JpaRepository<Password, Long> {
    public List<Password> findByUserId(Long id);
}
