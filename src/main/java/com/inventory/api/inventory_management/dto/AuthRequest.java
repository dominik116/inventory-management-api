package com.inventory.api.inventory_management.dto;

import lombok.Data;

@Data
public class AuthRequest {

    private String username;

    private String password;
}
