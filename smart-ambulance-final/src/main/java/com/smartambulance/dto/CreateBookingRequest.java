package com.smartambulance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookingRequest {
  private String hospitalName;
  private Double pickupLat;
  private Double pickupLng;
}
