package com.smartambulance.dto;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class AcceptanceMessage {
    private Long bookingId;
    private String driverName;
    private String driverPhone;
}
