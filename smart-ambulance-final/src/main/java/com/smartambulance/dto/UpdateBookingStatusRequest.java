package com.smartambulance.dto;

import com.smartambulance.entity.BookingStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBookingStatusRequest
{
  private BookingStatus status;
}
