package com.smartambulance.service;

import com.smartambulance.entity.AppUser;
import com.smartambulance.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

  private final AppUserRepository appUserRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
    this.appUserRepository = appUserRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return appUserRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
  }

  public AppUser createUser(String name, String email, String password, String role, String phone) {
    AppUser user = new AppUser();
    user.setName(name);
    user.setUsername(email);
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(password));
    user.setRole(role);
    user.setPhone(phone);
    return appUserRepository.save(user);
  }

  public Optional<AppUser> findByEmail(String email) {
    return appUserRepository.findByEmail(email);
  }

  public List<AppUser> getDrivers() {
    return appUserRepository.findByRole("DRIVER");
  }

  public AppUser getLoggedInUser() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    String username;
    if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
      username = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
    } else {
      username = principal.toString();
    }

    return appUserRepository.findByUsername(username).orElse(null);
  }
}