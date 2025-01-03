package com.inventory.api.inventory_management.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeCreateDto {

    private String username;

    private String name;

    private String surname;

    @Size(max = 9)
    private String nif;

    private String email;

    private Boolean enabled;

    private String password;
}
