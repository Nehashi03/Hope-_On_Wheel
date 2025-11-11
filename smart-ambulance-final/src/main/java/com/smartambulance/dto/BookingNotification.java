package com.smartambulance.dto;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class BookingNotification {
    private Long bookingId;
    private String patientName;
    private String patientPhone;
    private String hospital;
    private Double pickupLat;
    private Double pickupLng;
}
