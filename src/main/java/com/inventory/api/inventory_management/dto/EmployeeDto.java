package com.inventory.api.inventory_management.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    private Instant createdAt;

    private Instant updatedAt;

    private String role;
}
