package com.coral.backend.services;

import com.coral.backend.dtos.CheckSessionDTO;
import com.coral.backend.dtos.InvestorDTO;
import com.coral.backend.dtos.NotificationDTO;
import com.coral.backend.entities.EnterpriseUser;
import com.coral.backend.entities.InvestorUser;
import com.coral.backend.entities.Notification;
import com.coral.backend.entities.User;
import com.coral.backend.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private AuthService authService;

    @Autowired
    NotificationRepository notificationRepository;

    public ResponseEntity<Object> getNotifications(CheckSessionDTO checkSessionDTO) {
        EnterpriseUser user = (EnterpriseUser) authService.checkAuth(checkSessionDTO.getSessionToken());
        List<Notification> notifications = notificationRepository.findAllByEnterpriseOrderByTimeStampDesc(user);
        List<NotificationDTO> notificationDTOS= new ArrayList<>();
        for (Notification notification : notifications) {
            notification.setRead(true);
            notificationRepository.save(notification);
            NotificationDTO notificationDTO = getNotificationDTO(notification);
            notificationDTOS.add(notificationDTO);
        }
        return new ResponseEntity<>(notificationDTOS, HttpStatus.OK);
    }

    private static NotificationDTO getNotificationDTO(Notification notification) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setNotificationMessage(notification.getMessage());
        notificationDTO.setDate(notification.getTimeStamp());
        InvestorUser investor = notification.getInvestor();
        InvestorDTO investorDTO = new InvestorDTO();
        investorDTO.setName(investor.getName());
        investorDTO.setProfilePicture(investor.getProfileImageString());
        notificationDTO.setInvestor(investorDTO);
        return notificationDTO;
    }

    public ResponseEntity<Object> hasNotifications(CheckSessionDTO checkSessionDTO) {
        EnterpriseUser user = (EnterpriseUser) authService.checkAuth(checkSessionDTO.getSessionToken());
        List<Notification> notifications = notificationRepository.findAllByEnterpriseAndReadFalse(user);
        return new ResponseEntity<>(!notifications.isEmpty(), HttpStatus.OK);
    }
}
