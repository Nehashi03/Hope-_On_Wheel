package com.smartambulance.dto;
import lombok.Data;
@Data
public class LocationMessage {
    private Long bookingId;
    private double latitude;
    private double longitude;
}
