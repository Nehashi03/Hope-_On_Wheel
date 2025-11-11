package com.smartambulance.ws;

import com.smartambulance.dto.AcceptanceMessage;
import com.smartambulance.dto.BookingNotification;
import com.smartambulance.dto.LocationMessage;
import com.smartambulance.entity.Booking;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RealtimeGateway {

    private final SimpMessagingTemplate template;

    public RealtimeGateway(SimpMessagingTemplate template) { this.template = template; }

    public void forwardPatientLocation(LocationMessage msg) {
        template.convertAndSend("/topic/patient-location/" + msg.getBookingId(),
                Map.of("lat", msg.getLatitude(), "lng", msg.getLongitude()));
    }
    public void forwardDriverLocation(LocationMessage msg) {
        template.convertAndSend("/topic/driver-location/" + msg.getBookingId(),
                Map.of("lat", msg.getLatitude(), "lng", msg.getLongitude()));
    }

    public void broadcastNewBooking(Booking b) {
        var payload = new BookingNotification(
                b.getId(),
                b.getPatient()!=null ? b.getPatient().getName() : "Unknown",
                b.getPatient()!=null ? b.getPatient().getPhone() : "",
                b.getHospital(),
                b.getPickupLat(),
                b.getPickupLng()
        );
        template.convertAndSend("/topic/driver-requests", payload);
    }

    public void notifyAccepted(Booking b) {
        var payload = new AcceptanceMessage(
                b.getId(),
                b.getDriver()!=null ? b.getDriver().getName() : "Unknown",
                b.getDriver()!=null ? b.getDriver().getPhone() : ""
        );
        template.convertAndSend("/topic/booking/" + b.getId() + "/accepted", payload);
    }
}
