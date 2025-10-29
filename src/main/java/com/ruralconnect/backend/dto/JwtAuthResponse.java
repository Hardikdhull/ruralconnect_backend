package com.ruralconnect.backend.dto;

import lombok.Data;

@Data
public class JwtAuthResponse {

    private String token;
    private String tokenType = "Bearer";
    private String role; // So the app knows if the user is a "ROLE_USER" or "ROLE_ADMIN"

    // Constructor to easily create this object
    public JwtAuthResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }
}