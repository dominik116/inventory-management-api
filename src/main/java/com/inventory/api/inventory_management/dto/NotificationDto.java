package com.inventory.api.inventory_management.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Data
@RequiredArgsConstructor
public class NotificationDto {

    private Long id;

    private String message;

    private String status;

    private Instant createdAt;

    private Instant updatedAt;
}
