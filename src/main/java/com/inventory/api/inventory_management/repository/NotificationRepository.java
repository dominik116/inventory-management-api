package com.inventory.api.inventory_management.repository;

import com.inventory.api.inventory_management.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.status like %?1%")
    Page<Notification> findAllByNotificationStatus(String status, Pageable pageable);

    @Query("SELECT count(n) FROM Notification n WHERE n.status like %?1%")
    Integer getOpenNotificationNumber(String status);
}
