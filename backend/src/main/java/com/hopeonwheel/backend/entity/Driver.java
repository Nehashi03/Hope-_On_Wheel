package com.hopeonwheel.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.*;

@Entity
@Table(name = "drivers")
public class Driver {
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

    @NotBlank @Size(max = 50) @Column(unique = true)
    private String licenseNumber;

    @NotBlank @Size(max = 20)
    private String vehicleNumber;

    private boolean available = true;

    private Double latitude;
    private Double longitude;

    @OneToMany(mappedBy = "driver")
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

    public String getLicenseNumber(){ return licenseNumber; }
    public void setLicenseNumber(String licenseNumber){ this.licenseNumber = licenseNumber; }

    public String getVehicleNumber(){ return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber){ this.vehicleNumber = vehicleNumber; }

    public boolean isAvailable(){ return available; }
    public void setAvailable(boolean available){ this.available = available; }

    public Double getLatitude(){ return latitude; }
    public void setLatitude(Double latitude){ this.latitude = latitude; }

    public Double getLongitude(){ return longitude; }
    public void setLongitude(Double longitude){ this.longitude = longitude; }

    public List<Booking> getBookings(){ return bookings; }
    public void setBookings(List<Booking> bookings){ this.bookings = bookings; }
}
