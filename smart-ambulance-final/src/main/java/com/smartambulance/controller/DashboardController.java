package com.smartambulance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/patient/dashboard")
    public String patientDashboard() {
        return "cdashboard";
    }

    @GetMapping("/driver/dashboard")
    public String driverDashboard() {
        return "ddashboard";
    }
}
