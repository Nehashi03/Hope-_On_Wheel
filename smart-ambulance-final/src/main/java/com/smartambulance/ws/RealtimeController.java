package com.smartambulance.ws;

import com.smartambulance.dto.LocationMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class RealtimeController {

    private final RealtimeGateway gateway;

    public RealtimeController(RealtimeGateway gateway) {
        this.gateway = gateway;
    }

    @MessageMapping("/patient/location")
    public void patientLocation(LocationMessage msg) {
        gateway.forwardPatientLocation(msg);
    }

    @MessageMapping("/driver/location")
    public void driverLocation(LocationMessage msg) {
        gateway.forwardDriverLocation(msg);
    }
}
