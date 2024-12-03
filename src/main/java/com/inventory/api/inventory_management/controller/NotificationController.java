package com.inventory.api.inventory_management.controller;

import com.inventory.api.inventory_management.dto.NotificationCreateDto;
import com.inventory.api.inventory_management.dto.NotificationDto;
import com.inventory.api.inventory_management.dto.PagingDto;
import com.inventory.api.inventory_management.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class NotificationController {

    private final NotificationService notificationService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, path = "/notifications", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<NotificationDto> postNotification(@Valid @RequestBody final NotificationCreateDto dto) {
        return ResponseEntity.created(URI.create("/notifications")).body(this.notificationService.create(dto));
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/notifications", produces = {"application/json"})
    public ResponseEntity<PagingDto<NotificationDto>> findAllNotificationWithPagination(
            @RequestParam(name = "page", defaultValue = "0") final int page, @RequestParam(name = "size", defaultValue = "10") final int size) {
        return ResponseEntity.ok(this.notificationService.findAll(page, size));
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/notifications/{id}", produces = {"application/json"})
    public ResponseEntity<NotificationDto> findNotificationById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(this.notificationService.findById(id));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.PUT, path = "/notifications/{id}", produces = {"application/json"})
    public void updateNotificationStatus(@PathVariable("id") final Long id, @RequestParam(name = "status") final String status) {
        this.notificationService.updateStatus(id, status);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/notifications/count", produces = {"application/json"})
    public ResponseEntity<Integer> getOpenNotificationCount() {
        return ResponseEntity.ok(this.notificationService.getOpenNotificationNumber());
    }
}
