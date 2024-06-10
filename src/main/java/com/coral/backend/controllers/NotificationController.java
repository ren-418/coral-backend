package com.coral.backend.controllers;

import com.coral.backend.dtos.CheckSessionDTO;
import com.coral.backend.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @PostMapping("/get-notifications")
    public ResponseEntity<Object> getNotifications(@RequestBody CheckSessionDTO checkSessionDTO) {
        return notificationService.getNotifications(checkSessionDTO);
    }

    @PostMapping("/has-notifications")
    public ResponseEntity<Object> hasNotifications(@RequestBody CheckSessionDTO checkSessionDTO) {
        return notificationService.hasNotifications(checkSessionDTO);
    }
}
