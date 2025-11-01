package com.hopeonwheel.backend.controller;

import com.hopeonwheel.backend.dto.AuthDtos;
import com.hopeonwheel.backend.entity.Driver;
import com.hopeonwheel.backend.entity.Patient;
import com.hopeonwheel.backend.entity.AdminUser;
import com.hopeonwheel.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService auth;
    private final boolean allowAdminSignup;
    public AuthController(AuthService auth, @Value("${app.allowAdminSignup:false}") boolean allowAdminSignup){
        this.auth = auth; this.allowAdminSignup = allowAdminSignup;
    }

    @PostMapping("/register/patient")
    public ResponseEntity<Patient> registerPatient(@Valid @RequestBody AuthDtos.PatientRegister dto){
        return ResponseEntity.ok(auth.registerPatient(dto));
    }

    @PostMapping("/register/driver")
    public ResponseEntity<Driver> registerDriver(@Valid @RequestBody AuthDtos.DriverRegister dto){
        return ResponseEntity.ok(auth.registerDriver(dto));
    }

    // Optional and disabled by default; enable with app.allowAdminSignup=true
    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestParam String name, @RequestParam String email, @RequestParam String password){
        if (!allowAdminSignup) return ResponseEntity.status(403).body("Admin signup disabled");
        AdminUser admin = auth.registerAdmin(name, email, password);
        return ResponseEntity.ok(admin);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDtos.JwtResponse> login(@Valid @RequestBody AuthDtos.Login dto){
        return ResponseEntity.ok(auth.login(dto));
    }
}
