package com.smartambulance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {


    @GetMapping("/frontend")
    public String frontend() {
        return "frontend-index";
    }

    @GetMapping("/patient") public String patientDashboard() { return "cdashboard"; }
    @GetMapping("/patient/book") public String patientBook() { return "booking"; }
    @GetMapping("/patient/track") public String patientTrack() { return "track"; }
    @GetMapping("/driver") public String driverDashboard() { return "ddashboard"; }
}
