package com.hopeonwheel.backend.config;

import com.hopeonwheel.backend.entity.AdminUser;
import com.hopeonwheel.backend.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BootstrapConfig {

    @Bean

    CommandLineRunner bootstrapAdmin(AdminUserRepository admins, PasswordEncoder encoder,
                                     @Value("${app.admin.email:admin@how.local}") String email,
                                     @Value("${app.admin.password:Admin@123}") String password) {
        return args -> {
            admins.findByEmail(email).orElseGet(() -> {
                AdminUser a = new AdminUser();
                a.setName("Admin");
                a.setEmail(email);
                a.setPasswordHash(encoder.encode(password));
                return admins.save(a);
            });
        };
    }
}
