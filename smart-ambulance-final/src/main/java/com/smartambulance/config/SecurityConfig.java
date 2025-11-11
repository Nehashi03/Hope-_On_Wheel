package com.smartambulance.config;

import com.smartambulance.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    // ✅ Password encoder (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ Authentication provider (connects Spring Security with your database)
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // ✅ Authentication manager (required by Spring Boot 3+)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // ✅ Security filter chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable for simplicity (enable later for forms)
                .authorizeHttpRequests(auth -> auth
                        // Public pages
                        .requestMatchers("/", "/index.html", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/patient/login", "/driver/login", "/register", "/patient/register", "/driver/register").permitAll()
                        .requestMatchers("/h2-console/**").permitAll() // if H2 used

                        // Role-based pages
                        .requestMatchers("/cdashboard/**").hasRole("PATIENT")
                        .requestMatchers("/ddashboard/**").hasRole("DRIVER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )
                // ✅ Login configuration
                .formLogin(form -> form
                        .loginPage("/patient/login")            // Default login page
                        .loginProcessingUrl("/login")           // Form action URL (common for all)
                        .defaultSuccessUrl("/postLogin", true)  // Redirect after success
                        .failureUrl("/patient/login?error=true")// Redirect after failure
                        .permitAll()
                )

                // ✅ Logout configuration
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/patient/login?logout=true")
                        .permitAll()
                )

                // ✅ Optional (for H2 console)
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))

                // ✅ Attach authentication provider
                .authenticationProvider(authenticationProvider());

        return http.build();
    }
}
