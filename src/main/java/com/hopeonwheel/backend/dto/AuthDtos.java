package com.hopeonwheel.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.List;

public class AuthDtos {
    public static class PatientRegister {
        @NotBlank public String name;
        @NotBlank @Email public String email;
        @NotBlank @Size(min = 8) public String password;
        @NotBlank @Size(min = 8, max = 20) public String phone;
        public String bloodType;
        public String specialMedicalCondition;
    }

    public static class DriverRegister {
        @NotBlank public String name;
        @NotBlank @Email
        public String email;
        @NotBlank @Size(min = 8) public String password;
        @NotBlank @Size(min = 8, max = 20) public String phone;
        @NotBlank public String licenseNumber;
        @NotBlank public String vehicleNumber;
    }

    public static class Login {
        @NotBlank @Email public String email;
        @NotBlank @Size(min = 8) public String password;
        @NotBlank public String userType; // PATIENT or DRIVER or ADMIN
    }

    public static class JwtResponse {
        public String token;
        public String userType;
        public Long userId;
        public String name;
        public List<String> roles;
        public long expiresAtEpochSeconds;
        public JwtResponse(String token, String userType, Long userId, String name, List<String> roles, long exp){
            this.token = token; this.userType = userType; this.userId = userId; this.name = name; this.roles = roles; this.expiresAtEpochSeconds = exp;
        }
    }
}
