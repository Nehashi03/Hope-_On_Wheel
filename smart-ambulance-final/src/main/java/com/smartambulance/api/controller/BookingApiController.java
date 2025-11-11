package com.smartambulance.api.controller;

import com.smartambulance.entity.Booking;
import com.smartambulance.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingApiController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/patient/{email}")
    public List<Booking> getBookingsByPatientEmail(@PathVariable String email) {
        return bookingService.getBookingsByPatientEmail(email);
    }

    @GetMapping("/driver/{email}")
    public List<Booking> getBookingsByDriverEmail(@PathVariable String email) {
        return bookingService.getBookingsByDriverEmail(email);
    }

    @PutMapping("/{id}/status")
    public Booking updateStatus(@PathVariable Long id, @RequestParam String status) {
        return bookingService.updateBookingStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
    }
}
