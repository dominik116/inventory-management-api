package com.inventory.api.inventory_management.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

    private String token;

    private long expiresIn;

    private String role;

    public AuthResponse(String token, long expiresIn, String role) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.role = role;
    }
}
