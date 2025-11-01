package com.hopeonwheel.backend.repository;

import com.hopeonwheel.backend.entity.Booking;
import com.hopeonwheel.backend.entity.Driver;
import com.hopeonwheel.backend.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByPatient(Patient patient);
    List<Booking> findByDriver(Driver driver);
    List<Booking> findByStatus(Booking.Status status);
}
