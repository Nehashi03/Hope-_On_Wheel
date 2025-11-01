package com.hopeonwheel.backend.security;

public enum Role {
    PATIENT, DRIVER, ADMIN;
    public String authority(){ return "ROLE_" + this.name(); }
}
