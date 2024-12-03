package com.inventory.api.inventory_management.service;

import com.inventory.api.inventory_management.dto.NotificationCreateDto;
import com.inventory.api.inventory_management.dto.NotificationDto;
import com.inventory.api.inventory_management.dto.PagingDto;
import com.inventory.api.inventory_management.entity.Notification;
import com.inventory.api.inventory_management.mapper.NotificationMapper;
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

    public static final String OPEN = "open";

    private final NotificationRepository repository;

    private final NotificationMapper mapper;

    private final CacheManager cacheManager;

    public NotificationDto create(final NotificationCreateDto dto) {
        log.info("IN NotificationService: create");
        final Notification notification = this.repository.save(this.mapper.createDtoToEntity(dto));
        this.clearCache();
        return this.mapper.entityToDto(notification);
    }

    public NotificationDto findById(final Long id) {
        log.info("IN NotificationService: findById");
        return this.mapper.entityToDto(this.repository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Cacheable(value = "notificationCache", key = "'page:' + #page + ',size:' + #size")
    public PagingDto<NotificationDto> findAll(final int page, final int size) {
        log.info("IN NotificationService: findAll");
        final Page<Notification> notification = this.repository
                .findAllByNotificationStatus(OPEN, PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id")));
        return this.mapper.toPagingDto(notification);
    }

    public void updateStatus(final Long id, final String status) {
        log.info("IN NotificationService: updateStatus");
        final Notification notification = this.repository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (!notification.getStatus().equals(status)) {
            notification.setStatus(status);
        }
        this.repository.save(notification);
    }

    public Integer getOpenNotificationNumber() {
        log.info("IN NotificationService: getOpenNotificationNumber");
        return this.repository.getOpenNotificationNumber(OPEN);
    }

    private void clearCache() {
        Objects.requireNonNull(this.cacheManager.getCache("notificationCache")).clear();
        log.info("Cache 'notificationCache' has been cleared successfully.");
    }
}
