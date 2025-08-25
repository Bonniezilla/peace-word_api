package com.bonniezilla.aprendendospring.repositories;

import com.bonniezilla.aprendendospring.entities.Password;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PasswordRepository extends JpaRepository<Password, UUID> {
    public List<Password> findByUserId(UUID id);
    public List<Password> findByCategory(String category);
}
