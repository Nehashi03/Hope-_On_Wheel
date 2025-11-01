package com.hopeonwheel.backend.security;

public class AuthUser {
    private final Long userId;
    private final String userType; // PATIENT, DRIVER, ADMIN
    private final String name;

    public AuthUser(Long userId, String userType, String name)
    {
        this.userId = userId; this.userType = userType; this.name = name;
    }

    public Long getUserId()
    { return userId;
    }
    public String getUserType()
    { return userType;
    }
    public String getName()
    { return name;
    }
}
