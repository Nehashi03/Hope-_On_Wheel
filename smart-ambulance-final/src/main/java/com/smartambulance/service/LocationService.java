package com.smartambulance.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LocationService {

    private final SimpMessagingTemplate messagingTemplate;

    public LocationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void broadcastDriverLocation(Long bookingId, Map<String, Double> coords) {
        messagingTemplate.convertAndSend("/topic/driver-location/" + bookingId, coords);
    }

    public void broadcastPatientLocation(Long bookingId, Map<String, Double> coords) {
        messagingTemplate.convertAndSend("/topic/patient-location/" + bookingId, coords);
    }
}
