package com.hopeonwheel.backend.service;

import com.hopeonwheel.backend.dto.BookingDtos;
import com.hopeonwheel.backend.entity.Driver;
import com.hopeonwheel.backend.repository.DriverRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class DriverService {
    private final DriverRepository drivers;
    public DriverService(DriverRepository drivers){ this.drivers = drivers; }

    public List<Driver> availableDrivers(){ return drivers.findByAvailableTrue(); }

    @Transactional
    public Driver setAvailability(Long id, boolean available){
        Driver d = drivers.findById(id).orElseThrow(() -> new IllegalArgumentException("Driver not found"));
        d.setAvailable(available);
        return drivers.save(d);
    }

    @Transactional
    public Driver updateLocation(Long id, BookingDtos.DriverLocation loc){
        Driver d = drivers.findById(id).orElseThrow(() -> new IllegalArgumentException("Driver not found"));
        d.setLatitude(loc.latitude);
        d.setLongitude(loc.longitude);
        return drivers.save(d);
    }
}
