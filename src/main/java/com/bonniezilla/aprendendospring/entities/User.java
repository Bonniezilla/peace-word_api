package com.bonniezilla.aprendendospring.entities;


import com.bonniezilla.aprendendospring.dtos.UserLoginDTO;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String username;

    @Enumerated(EnumType.STRING) // Save as text in DB
    @Column(nullable = false)
    private Role role;

    public UUID getId() {
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

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }

    public void setRole(Role role ) {this.role = role;}

    // Default constructor
    public User(){}

    public User(UserLoginDTO user){
        this.email = user.email();
        this.username = user.password();
    }

    // Constructor with args for creating users
    public User(String email, String username){
        this.email = email;
        this.username = username;
    }

    // Constructor to create user with id (ONLY FOR TESTS)
    protected User(UUID id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }
}
