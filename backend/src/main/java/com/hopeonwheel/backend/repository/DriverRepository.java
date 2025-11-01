package com.hopeonwheel.backend.repository;

import com.hopeonwheel.backend.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Driver> findByAvailableTrue();
}
