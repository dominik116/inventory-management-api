package com.inventory.api.inventory_management.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class EmployeeDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String name;

    private String surname;

    private String nif;

    private String email;

    private Boolean enabled;

    private String password;
}
