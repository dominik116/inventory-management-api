package com.inventory.api.inventory_management.mapper;

import com.inventory.api.inventory_management.dto.NotificationCreateDto;
import com.inventory.api.inventory_management.dto.NotificationDto;
import com.inventory.api.inventory_management.dto.PagingDto;
import com.inventory.api.inventory_management.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    Notification createDtoToEntity(NotificationCreateDto dto);

    NotificationDto entityToDto(Notification notification);

    default PagingDto<NotificationDto> toPagingDto(Page<Notification> notifications) {
        List<NotificationDto> notificationDtos = notifications.getContent().stream()
                .map(NotificationMapper::toNotificationDto)
                .collect(Collectors.toList());

        PagingDto<NotificationDto> pagingDto = new PagingDto<>();
        pagingDto.setContent(notificationDtos);
        pagingDto.setPage(notifications.getNumber());
        pagingDto.setSize(notifications.getSize());
        pagingDto.setSort(notifications.getSort().toString());
        pagingDto.setTotal((int) notifications.getTotalElements());

        return pagingDto;
    }

    private static NotificationDto toNotificationDto(Notification notification) {
        final NotificationDto dto = new NotificationDto();
        dto.setId(notification.getId());
        dto.setMessage(notification.getMessage());
        dto.setStatus(notification.getStatus());
        dto.setCreatedAt(notification.getCreatedAt().toInstant());
        dto.setUpdatedAt(notification.getUpdatedAt().toInstant());

        return dto;
    }
}
