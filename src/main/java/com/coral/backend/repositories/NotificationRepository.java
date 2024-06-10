package com.coral.backend.repositories;

import com.coral.backend.entities.EnterpriseUser;
import com.coral.backend.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.enterprise = :enterprise ORDER BY n.timeStamp DESC")
    List<Notification> findAllByEnterpriseOrderByTimeStampDesc(@Param("enterprise") EnterpriseUser enterprise);

    @Query("SELECT n FROM Notification n WHERE n.enterprise = :enterprise AND n.read = false")
    List<Notification> findAllByEnterpriseAndReadFalse(@Param("enterprise") EnterpriseUser enterprise);
}
