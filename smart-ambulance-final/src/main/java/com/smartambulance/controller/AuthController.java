package com.smartambulance.controller;

import com.smartambulance.entity.AppUser;
import com.smartambulance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

  private final UserService userService;

  @Autowired
  public AuthController(UserService userService) {
    this.userService = userService;
  }

  // Show login page
  @GetMapping("patient/login")
  public String loginPage() {
    return "clogin"; // returns login.html
  }


  @GetMapping("/driver/login")
  public String driverLoginPage() {
    return "dlogin";
  }

  // Show register page
  @GetMapping("patient/register")
  public String registerPage(Model model) {
    model.addAttribute("user", new AppUser());
    return "cregister"; // returns register.html
  }


  @GetMapping("/driver/register")
  public String driverRegisterPage(Model model) {
    model.addAttribute("user", new AppUser());
    return "dregister";
  }

  // Handle registration form
  @PostMapping("/register")
  public String registerUser(@ModelAttribute AppUser user) {
    userService.createUser(
            user.getName(),
            user.getEmail(),
            user.getPassword(),
            "PATIENT", // default role
            user.getPhone()
    );
    return "redirect:/login?success";
  }


  @PostMapping("/driver/register")
  public String registerDriver(@ModelAttribute AppUser user) {
    userService.createUser(
            user.getName(),
            user.getEmail(),
            user.getPassword(),
            "DRIVER",
            user.getPhone()
    );
    return "redirect:/driver/login?success";
  }

  // After login — redirect based on role
  @GetMapping("/postLogin")
  public String postLogin(Authentication auth) {
    if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PATIENT"))) {
      return "redirect:/cdashboard";
    } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_DRIVER"))) {
      return "redirect:/ddashboard";
    } else {
      return "redirect:/admin";
    }
  }


  // After logout
  @GetMapping("/logout-success")
  public String logoutSuccess() {
    return "redirect:/login?logout";
  }
}
