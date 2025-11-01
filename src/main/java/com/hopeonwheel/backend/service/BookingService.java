package com.hopeonwheel.backend.service;

import com.hopeonwheel.backend.dto.BookingDtos;
import com.hopeonwheel.backend.entity.Booking;
import com.hopeonwheel.backend.entity.Driver;
import com.hopeonwheel.backend.entity.Patient;
import com.hopeonwheel.backend.repository.BookingRepository;
import com.hopeonwheel.backend.repository.DriverRepository;
import com.hopeonwheel.backend.repository.PatientRepository;
import com.hopeonwheel.backend.security.AuthUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookings;
    private final PatientRepository patients;
    private final DriverRepository drivers;

    public BookingService(BookingRepository bookings, PatientRepository patients, DriverRepository drivers) {
        this.bookings = bookings;
        this.patients = patients;
        this.drivers = drivers;
    }

    @Transactional
    public Booking create(Long patientId, BookingDtos.CreateBooking dto) {
        Patient p = patients.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        Booking b = new Booking();
        b.setPatient(p);
        b.setPickupLocation(dto.pickupLocation);
        b.setDestination(dto.destination);
        b.setPickupLat(dto.pickupLat);
        b.setPickupLng(dto.pickupLng);
        b.setDestLat(dto.destLat);
        b.setDestLng(dto.destLng);
        b.setScheduledAt(dto.scheduledAt);
        b.setNotes(dto.notes);
        return bookings.save(b);
    }

    public List<Booking> forPatient(Long patientId) {
        Patient p = patients.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        return bookings.findByPatient(p);
    }

    public List<Booking> forDriver(Long driverId) {
        Driver d = drivers.findById(driverId).orElseThrow(() -> new IllegalArgumentException("Driver not found"));
        return bookings.findByDriver(d);
    }

    @Transactional
    public Booking assignDriver(Long bookingId, Long driverId, AuthUser caller) {
        Booking b = bookings.findById(bookingId).orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        Driver d = drivers.findById(driverId).orElseThrow(() -> new IllegalArgumentException("Driver not found"));
        // Role rule: drivers can self-assign only
        if ("DRIVER".equals(caller.getUserType()) && !caller.getUserId().equals(driverId)) {
            throw new IllegalArgumentException("Drivers can only assign themselves to a booking");
        }
        b.setDriver(d);
        b.setStatus(Booking.Status.ACCEPTED);
        return bookings.save(b);
    }

    @Transactional
    public Booking updateStatus(Long bookingId, Booking.Status status, AuthUser caller) {
        Booking b = bookings.findById(bookingId).orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        switch (caller.getUserType()) {
            case "DRIVER" -> {
                if (b.getDriver() == null || !b.getDriver().getId().equals(caller.getUserId())) {
                    throw new IllegalArgumentException("Only the assigned driver can update this booking");
                }
                b.setStatus(status);
            }
            case "PATIENT" -> {
                if (!b.getPatient().getId().equals(caller.getUserId())) {
                    throw new IllegalArgumentException("Only the booking owner can modify this booking");
                }
                if (status != Booking.Status.CANCELLED) {
                    throw new IllegalArgumentException("Patients can only cancel their bookings");
                }
                b.setStatus(Booking.Status.CANCELLED);
            }
            case "ADMIN" -> {
                b.setStatus(status);
            }
            default -> throw new IllegalArgumentException("Unauthorized actor");
        }
        return bookings.save(b);
    }
}
