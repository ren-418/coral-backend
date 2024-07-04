package com.coral.backend.repositories;

import com.coral.backend.entities.EnterpriseUser;
import com.coral.backend.entities.Notification;
import com.coral.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByToOrderByNotificationIdDesc(User to);

    List<Notification> findAllByToAndRead( User to, boolean read);
}
