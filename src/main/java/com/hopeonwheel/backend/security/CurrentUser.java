package com.hopeonwheel.backend.security;

public class CurrentUser {
    private final Long userId;
    private final String role; // PATIENT or DRIVER (or ADMIN if you add)
    private final String name;
    private final String email;

    public CurrentUser(Long userId, String role, String name, String email) {
        this.userId = userId; this.role = role; this.name = name; this.email = email;
    }
    public Long getUserId(){ return userId; }
    public String getRole(){ return role; }
    public String getName(){ return name; }
    public String getEmail(){ return email; }
}
