package com.bonniezilla.aprendendospring.entities;


import com.bonniezilla.aprendendospring.dtos.UserRequestDTO;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Default constructor
    public User(){}

    public User(UserRequestDTO user){
        this.email = user.email();
        this.username = user.username();
    }

    // Constructor with args for creating users
    public User(String email, String username){
        this.email = email;
        this.username = username;
    }

    // Constructor to create user with id (ONLY FOR TESTS)
    protected User(Long id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }
}
