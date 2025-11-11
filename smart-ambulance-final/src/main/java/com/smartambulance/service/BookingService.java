package com.smartambulance.service;

import com.smartambulance.entity.AppUser;
import com.smartambulance.entity.Booking;
import com.smartambulance.entity.BookingStatus;
import com.smartambulance.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

  @Autowired
  private BookingRepository bookingRepository;

  // Create new booking
  public Booking createBooking(Booking booking) {
    booking.setStatus(BookingStatus.valueOf("PENDING"));
    return bookingRepository.save(booking);
  }

  // Get all bookings
  public List<Booking> getAllBookings() {
    return bookingRepository.findAll();
  }

  // Get bookings by patient email
  public List<Booking> getBookingsByPatientEmail(String email) {
    return bookingRepository.findByPatientEmail(email);
  }

  // Get bookings by driver email
  public List<Booking> getBookingsByDriverEmail(String email) {
    return bookingRepository.findByDriverEmail(email);
  }

  // Get booking by ID
  public Optional<Booking> getBookingById(Long id) {
    return bookingRepository.findById(id);
  }

  // Update booking status (e.g. ACCEPTED, COMPLETED, CANCELLED)
  public Booking updateBookingStatus(Long id, String status) {
    Booking booking = bookingRepository.findById(id).orElseThrow();
    booking.setStatus(BookingStatus.valueOf(status));
    return bookingRepository.save(booking);
  }

  // Delete booking
  public void deleteBooking(Long id) {
    bookingRepository.deleteById(id);
  }

  // ✅ Assign a driver to a booking
  public Booking assignToDriver(Long bookingId, AppUser driver) {
    Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    booking.setDriver(driver);
    booking.setStatus(BookingStatus.valueOf("ASSIGNED"));
    return bookingRepository.save(booking);
  }

  public Object pending()
  {
    return bookingRepository.findByStatus(BookingStatus.PENDING.name());

  }
}
