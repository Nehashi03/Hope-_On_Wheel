package com.hopeonwheel.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.*;

@Entity
@Table(name = "patients")                                                                               
public class Patient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(max = 100)
    private String name;

    @NotBlank @Email @Column(unique = true, length = 150)
    private String email;

    @NotBlank @Size(min = 8, max = 255)
    private String passwordHash;

    @NotBlank @Size(min = 8, max = 20)
    private String phone;

    @Size(max = 10)
    private String bloodType;

    @Size(max = 500)
    private String specialMedicalCondition;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    // getters/setters
    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public String getEmail(){ return email; }
    public void setEmail(String email){ this.email = email; }

    public String getPasswordHash(){ return passwordHash; }
    public void setPasswordHash(String passwordHash){ this.passwordHash = passwordHash; }

    public String getPhone(){ return phone; }
    public void setPhone(String phone){ this.phone = phone; }

    public String getBloodType(){ return bloodType; }
    public void setBloodType(String bloodType){ this.bloodType = bloodType; }

    public String getSpecialMedicalCondition(){ return specialMedicalCondition; }
    public void setSpecialMedicalCondition(String s){ this.specialMedicalCondition = s; }

    public List<Booking> getBookings(){ return bookings; }
    public void setBookings(List<Booking> bookings){ this.bookings = bookings; }
}
