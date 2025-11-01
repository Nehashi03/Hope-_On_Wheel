package com.hopeonwheel.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    public enum Status { PENDING, ACCEPTED, EN_ROUTE, COMPLETED, CANCELLED }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(max = 255)
    private String pickupLocation;

    @NotBlank @Size(max = 255)
    private String destination;

    private Double pickupLat;
    private Double pickupLng;
    private Double destLat;
    private Double destLng;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime scheduledAt;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Status status = Status.PENDING;

    @ManyToOne(optional = false)
    private Patient patient;

    @ManyToOne
    private Driver driver;

    @Size(max = 500)
    private String notes;

    // getters/setters
    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }

    public String getPickupLocation(){ return pickupLocation; }
    public void setPickupLocation(String pickupLocation){ this.pickupLocation = pickupLocation; }

    public String getDestination(){ return destination; }
    public void setDestination(String destination){ this.destination = destination; }

    public Double getPickupLat(){ return pickupLat; }
    public void setPickupLat(Double pickupLat){ this.pickupLat = pickupLat; }

    public Double getPickupLng(){ return pickupLng; }
    public void setPickupLng(Double pickupLng){ this.pickupLng = pickupLng; }

    public Double getDestLat(){ return destLat; }
    public void setDestLat(Double destLat){ this.destLat = destLat; }

    public Double getDestLng(){ return destLng; }
    public void setDestLng(Double destLng){ this.destLng = destLng; }

    public LocalDateTime getCreatedAt(){ return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt){ this.createdAt = createdAt; }

    public LocalDateTime getScheduledAt(){ return scheduledAt; }
    public void setScheduledAt(LocalDateTime scheduledAt){ this.scheduledAt = scheduledAt; }

    public Status getStatus(){ return status; }
    public void setStatus(Status status){ this.status = status; }

    public Patient getPatient(){ return patient; }
    public void setPatient(Patient patient){ this.patient = patient; }

    public Driver getDriver(){ return driver; }
    public void setDriver(Driver driver){ this.driver = driver; }

    public String getNotes(){ return notes; }
    public void setNotes(String notes){ this.notes = notes; }
}
