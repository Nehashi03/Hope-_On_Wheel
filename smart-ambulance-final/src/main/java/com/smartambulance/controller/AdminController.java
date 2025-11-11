package com.smartambulance.controller;

import com.smartambulance.entity.AppUser;
import com.smartambulance.service.BookingService;
import com.smartambulance.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    private final BookingService bookingService;
    private final UserService userService;

    public AdminController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    // ------------------- ADMIN DASHBOARD -------------------
    @GetMapping("/admin")
    public String dashboard(Model model) {
        model.addAttribute("bookings", bookingService.pending());
        model.addAttribute("drivers", userService.getDrivers());
        return "admin"; // admin.html template
    }

    // ------------------- MANUAL ASSIGN DRIVER -------------------
    @PostMapping("/admin/assign")
    public String assignDriver(@RequestParam Long bookingId,
                               @RequestParam String driverEmail) {
        AppUser driver = userService.findByEmail(driverEmail)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found: " + driverEmail));
        bookingService.assignToDriver(bookingId, driver);
        return "redirect:/admin";
    }
}
