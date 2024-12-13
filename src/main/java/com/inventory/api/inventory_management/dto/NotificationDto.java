package com.inventory.api.inventory_management.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Data
@RequiredArgsConstructor
public class NotificationDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String subject;

    private String message;

    private String status;

    private String username;

    private Instant createdAt;

    private Instant updatedAt;
}
