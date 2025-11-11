package com.smartambulance.config;

import com.smartambulance.entity.AppUser;
import com.smartambulance.entity.Role;
import com.smartambulance.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(AppUserRepository users, PasswordEncoder encoder) {
        return args -> {

            // ✅ Add default users
            if (users.findByEmail("admin@smart.local").isEmpty()) {
                AppUser admin = new AppUser();
                admin.setName("Admin");
                admin.setEmail("admin@smart.local");
                admin.setUsername("admin@smart.local");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRole(Role.ADMIN.name());
                admin.setPhone("0710000000");
                users.save(admin);
            }

            if (users.findByEmail("driver@smart.local").isEmpty()) {
                AppUser driver = new AppUser();
                driver.setName("Driver One");
                driver.setEmail("driver@smart.local");
                driver.setUsername("driver@smart.local");
                driver.setPassword(encoder.encode("driver123"));
                driver.setRole(Role.DRIVER.name());
                driver.setPhone("0778888888");
                users.save(driver);
            }

            if (users.findByEmail("patient@smart.local").isEmpty()) {
                AppUser patient = new AppUser();
                patient.setName("Patient One");
                patient.setEmail("patient@smart.local");
                patient.setUsername("patient@smart.local");
                patient.setPassword(encoder.encode("patient123"));
                patient.setRole(Role.PATIENT.name());
                patient.setPhone("0771234567");
                users.save(patient);
            }

            System.out.println("✅ Default Users Loaded");
        };
    }
}
