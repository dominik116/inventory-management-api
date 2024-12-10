package com.inventory.api.inventory_management.service;

import com.inventory.api.inventory_management.dto.NotificationCreateDto;
import com.inventory.api.inventory_management.dto.NotificationDto;
import com.inventory.api.inventory_management.dto.PagingDto;
import com.inventory.api.inventory_management.entity.Employee;
import com.inventory.api.inventory_management.entity.Notification;
import com.inventory.api.inventory_management.mapper.NotificationMapper;
import com.inventory.api.inventory_management.repository.EmployeeRepository;
import com.inventory.api.inventory_management.repository.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private static final String OPEN_STATUS = "open";

    private final NotificationRepository repository;

    private final EmployeeRepository employeeRepository;

    private final NotificationMapper mapper;

    private final CacheManager cacheManager;

    public NotificationDto create(final String username, final NotificationCreateDto dto) {
        log.info("IN NotificationService: create");
        final Employee employee = this.employeeRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        final Notification entity = this.mapper.createDtoToEntity(dto);
        entity.setEmployee(employee);
        entity.setStatus(OPEN_STATUS);
        final Notification notification = this.repository.save(entity);
        this.clearCache();
        return this.mapper.entityToDto(notification);
    }

    public NotificationDto findById(final Long id) {
        log.info("IN NotificationService: findById");
        return this.mapper.entityToDto(this.repository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Cacheable(value = "notificationCache", key = "'page:' + #page + ',size:' + #size")
    public PagingDto<NotificationDto> findAll(final int page, final int size, final String username) {
        log.info("IN NotificationService: findAll");
        Page<Notification> notifications;
        if (username != null) {
            notifications = this.repository.findAllByStatusAndUsername(OPEN_STATUS, username,
                    PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id")));
        } else {
            notifications = this.repository
                    .findAllByNotificationStatus(OPEN_STATUS, PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id")));
        }

        return this.mapper.toPagingDto(notifications);
    }

    public void updateStatus(final Long id, final String status) {
        log.info("IN NotificationService: updateStatus");
        final Notification notification = this.repository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (!notification.getStatus().equals(status)) {
            notification.setStatus(status);
        }
        this.repository.save(notification);
    }

    public void updateNotification(final Long id, final NotificationCreateDto dto) {
        log.info("IN NotificationService: updateNotification");
        final Notification notification = this.repository.findById(id).orElseThrow(EntityNotFoundException::new);
        this.updateEntityFields(notification, dto);
        this.repository.save(notification);
        this.clearCache();
        log.info("OUT NotificationService: updateNotification");
    }

    public Integer getOpenNotificationNumberByUsername(final String username) {
        log.info("IN NotificationService: getOpenNotificationNumberByUsername");
        return this.repository.getOpenNotificationNumberByUsername(OPEN_STATUS, username);
    }

    public Integer getOpenNotificationNumber() {
        log.info("IN NotificationService: getOpenNotificationNumber");
        return this.repository.getOpenNotificationNumber(OPEN_STATUS);
    }

    private void clearCache() {
        Objects.requireNonNull(this.cacheManager.getCache("notificationCache")).clear();
        log.info("Cache 'notificationCache' has been cleared successfully.");
    }

    private void updateEntityFields(final Notification notification, final NotificationCreateDto dto) {
        if (dto.getMessage() != null && !dto.getMessage().equals(notification.getMessage())) {
            notification.setMessage(dto.getMessage());
        }
    }
}
