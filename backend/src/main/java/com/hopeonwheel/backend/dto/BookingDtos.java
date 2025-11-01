package com.hopeonwheel.backend.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class BookingDtos {
    public static class CreateBooking {
        @NotBlank public String pickupLocation;
        @NotBlank public String destination;
        public Double pickupLat;
        public Double pickupLng;
        public Double destLat;
        public Double destLng;
        public LocalDateTime scheduledAt;
        public String notes;
    }

    public static class AssignDriver {
        @NotNull public Long driverId;
    }

    public static class UpdateStatus {
        @NotBlank public String status; // PENDING, ACCEPTED, EN_ROUTE, COMPLETED, CANCELLED
    }

    public static class DriverLocation {
        @NotNull public Double latitude;
        @NotNull public Double longitude;
    }
}
