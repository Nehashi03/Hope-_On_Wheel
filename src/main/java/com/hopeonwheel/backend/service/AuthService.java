package com.hopeonwheel.backend.service;

import com.hopeonwheel.backend.dto.AuthDtos;
import com.hopeonwheel.backend.entity.AdminUser;
import com.hopeonwheel.backend.entity.Driver;
import com.hopeonwheel.backend.entity.Patient;
import com.hopeonwheel.backend.repository.AdminUserRepository;
import com.hopeonwheel.backend.repository.DriverRepository;
import com.hopeonwheel.backend.repository.PatientRepository;
import com.hopeonwheel.backend.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthService {
    private final PatientRepository patients;
    private final DriverRepository drivers;
    private final AdminUserRepository admins;
    private final PasswordService passwords;
    private final JwtService jwt;
    private final boolean allowAdminSignup;

    public AuthService(PatientRepository patients, DriverRepository drivers, AdminUserRepository admins,
                       PasswordService passwords, JwtService jwt,
                       @Value("${app.allowAdminSignup:false}") boolean allowAdminSignup) {
        this.patients = patients;
        this.drivers = drivers;
        this.admins = admins;
        this.passwords = passwords;
        this.jwt = jwt;
        this.allowAdminSignup = allowAdminSignup;
    }

    @Transactional
    public Patient registerPatient(AuthDtos.PatientRegister dto) {
        if (patients.existsByEmail(dto.email)) throw new IllegalArgumentException("Email already in use");
        Patient p = new Patient();
        p.setName(dto.name);
        p.setEmail(dto.email);
        p.setPasswordHash(passwords.hash(dto.password));
        p.setPhone(dto.phone);
        p.setBloodType(dto.bloodType);
        p.setSpecialMedicalCondition(dto.specialMedicalCondition);
        return patients.save(p);
    }

    @Transactional
    public Driver registerDriver(AuthDtos.DriverRegister dto) {
        if (drivers.existsByEmail(dto.email)) throw new IllegalArgumentException("Email already in use");
        Driver d = new Driver();
        d.setName(dto.name);
        d.setEmail(dto.email);
        d.setPasswordHash(passwords.hash(dto.password));
        d.setPhone(dto.phone);
        d.setLicenseNumber(dto.licenseNumber);
        d.setVehicleNumber(dto.vehicleNumber);
        d.setAvailable(true);
        return drivers.save(d);
    }

    @Transactional
    public AdminUser registerAdmin(String name, String email, String password) {
        if (!allowAdminSignup) throw new IllegalArgumentException("Admin signup is disabled");
        if (admins.findByEmail(email).isPresent()) throw new IllegalArgumentException("Email already in use");
        AdminUser a = new AdminUser();
        a.setName(name);
        a.setEmail(email);
        a.setPasswordHash(passwords.hash(password));
        return admins.save(a);
    }

    @Transactional(readOnly = true)
    public AuthDtos.JwtResponse login(AuthDtos.Login dto) {
        String type = dto.userType.trim().toUpperCase();
        Long userId;
        String name;
        List<String> roles;
        switch (type) {
            case "PATIENT" -> {
                Patient p = patients.findByEmail(dto.email).orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
                if (!passwords.matches(dto.password, p.getPasswordHash())) throw new IllegalArgumentException("Invalid credentials");
                userId = p.getId();
                name = p.getName();
                roles = List.of("PATIENT");
            }
            case "DRIVER" -> {
                Driver d = drivers.findByEmail(dto.email).orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
                if (!passwords.matches(dto.password, d.getPasswordHash())) throw new IllegalArgumentException("Invalid credentials");
                userId = d.getId();
                name = d.getName();
                roles = List.of("DRIVER");
            }
            case "ADMIN" -> {
                AdminUser a = admins.findByEmail(dto.email).orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
                if (!passwords.matches(dto.password, a.getPasswordHash())) throw new IllegalArgumentException("Invalid credentials");
                userId = a.getId();
                name = a.getName();
                roles = List.of("ADMIN");
            }
            default -> throw new IllegalArgumentException("Unknown userType (use PATIENT, DRIVER or ADMIN)");
        }
        String token = jwt.create(userId, type, name, roles);
        long exp = jwt.getExpirationEpochSeconds(token);
        return new AuthDtos.JwtResponse(token, type, userId, name, roles, exp);
    }
}
