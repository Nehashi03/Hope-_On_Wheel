package com.hopeonwheel.backend.controller;

import com.hopeonwheel.backend.dto.BookingDtos;
import com.hopeonwheel.backend.entity.Driver;
import com.hopeonwheel.backend.security.AuthUser;
import com.hopeonwheel.backend.service.DriverService;
import com.hopeonwheel.backend.sse.TrackingHub;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverService drivers;
    private final TrackingHub hub;

    public DriverController(DriverService drivers, TrackingHub hub){
        this.drivers = drivers;
        this.hub = hub;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/available")
    public ResponseEntity<List<Driver>> available(){
        return ResponseEntity.ok(drivers.availableDrivers());
    }

    @PreAuthorize("hasRole('DRIVER')")
    @PatchMapping("/me/availability")
    public ResponseEntity<Driver> availability(@RequestParam boolean available, Authentication auth){
        AuthUser au = (AuthUser) auth.getPrincipal();
        return ResponseEntity.ok(drivers.setAvailability(au.getUserId(), available));
    }

    @PreAuthorize("hasRole('DRIVER')")
    @PostMapping("/me/location")
    public ResponseEntity<Driver> updateLocation(@Valid @RequestBody BookingDtos.DriverLocation loc, Authentication auth){
        AuthUser au = (AuthUser) auth.getPrincipal();
        Driver d = drivers.updateLocation(au.getUserId(), loc);
        return ResponseEntity.ok(d);
    }

    // Subscribe to live updates for a booking (SSE)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/track/{bookingId}")
    public SseEmitter subscribe(@PathVariable Long bookingId){
        return hub.subscribe(bookingId);
    }
}
