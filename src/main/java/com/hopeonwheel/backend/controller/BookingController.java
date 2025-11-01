package com.hopeonwheel.backend.controller;

import com.hopeonwheel.backend.dto.BookingDtos;
import com.hopeonwheel.backend.entity.Booking;
import com.hopeonwheel.backend.security.AuthUser;
import com.hopeonwheel.backend.service.BookingService;
import com.hopeonwheel.backend.sse.TrackingHub;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookings;
    private final TrackingHub hub;

    public BookingController(BookingService bookings, TrackingHub hub) {
        this.bookings = bookings;
        this.hub = hub;
    }

    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping
    public ResponseEntity<Booking> create(@Valid @RequestBody BookingDtos.CreateBooking dto,
                                          Authentication authentication){
        AuthUser au = (AuthUser) authentication.getPrincipal();
        return ResponseEntity.ok(bookings.create(au.getUserId(), dto));
    }

    @PreAuthorize("hasAnyRole('PATIENT','DRIVER')")
    @GetMapping("/me")
    public ResponseEntity<List<Booking>> myBookings(Authentication authentication){
        AuthUser au = (AuthUser) authentication.getPrincipal();
        if ("PATIENT".equals(au.getUserType())) {
            return ResponseEntity.ok(bookings.forPatient(au.getUserId()));
        } else {
            return ResponseEntity.ok(bookings.forDriver(au.getUserId()));
        }
    }

    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
    @PostMapping("/{bookingId}/assign")
    public ResponseEntity<Booking> assign(@PathVariable Long bookingId, @Valid @RequestBody BookingDtos.AssignDriver dto, Authentication authentication){
        AuthUser au = (AuthUser) authentication.getPrincipal();
        return ResponseEntity.ok(bookings.assignDriver(bookingId, dto.driverId, au));
    }

    @PreAuthorize("hasAnyRole('DRIVER','PATIENT','ADMIN')")
    @PatchMapping("/{bookingId}/status")
    public ResponseEntity<Booking> status(@PathVariable Long bookingId, @Valid @RequestBody BookingDtos.UpdateStatus dto,
                                          Authentication authentication){
        Booking.Status st = Booking.Status.valueOf(dto.status.toUpperCase());
        AuthUser au = (AuthUser) authentication.getPrincipal();
        var updated = bookings.updateStatus(bookingId, st, au);
        hub.send(bookingId, updated.getStatus().name()); // notify subscribers
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasAnyRole('DRIVER','PATIENT','ADMIN')")
    @PostMapping("/{bookingId}/location")
    public ResponseEntity<String> location(@PathVariable Long bookingId, @Valid @RequestBody BookingDtos.DriverLocation loc){
        hub.send(bookingId, String.format("{\"lat\":%s,\"lng\":%s}", String.valueOf(loc.latitude), String.valueOf(loc.longitude)));
        return ResponseEntity.ok("ok");
    }
}
