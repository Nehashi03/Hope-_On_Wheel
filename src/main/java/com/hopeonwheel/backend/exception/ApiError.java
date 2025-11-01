package com.hopeonwheel.backend.exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ApiError {

    public String message;
    public LocalDateTime timestamp = LocalDateTime.now();
    public Map<String, ?> details;

    public ApiError(String message) {
        this.message = message;
    }

    public ApiError(String message, Map<String, ?> details) {
        this.message = message;
        this.details = details;
    }
}
