package com.hopeonwheel.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "admins")
public class AdminUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(max = 100)
    private String name;

    @NotBlank @Email @Column(unique = true, length = 150)
    private String email;

    @NotBlank @Size(min = 8, max = 255)
    private String passwordHash;

    // getters/setters
    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public String getEmail(){ return email; }
    public void setEmail(String email){ this.email = email; }

    public String getPasswordHash(){ return passwordHash; }
    public void setPasswordHash(String passwordHash){ this.passwordHash = passwordHash; }
}
