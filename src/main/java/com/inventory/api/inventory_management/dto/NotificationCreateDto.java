package com.inventory.api.inventory_management.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class NotificationCreateDto {

    private String subject;

    private String message;

    private String status;

    private String username;
}
