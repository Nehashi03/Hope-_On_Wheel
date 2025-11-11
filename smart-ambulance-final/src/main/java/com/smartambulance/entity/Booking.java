package com.smartambulance.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity @Data
public class Booking {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  private AppUser patient;

  @ManyToOne
  private AppUser driver;

  private String hospital;

  @Enumerated(EnumType.STRING)
  private BookingStatus status;

  // patient chosen pickup
  private Double pickupLat;
  private Double pickupLng;

  private Instant createdAt;

  @PrePersist
  public void prePersist() {
    if (createdAt == null) createdAt = Instant.now();
    if (status == null) status = BookingStatus.PENDING;
  }
}
