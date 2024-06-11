package com.coral.backend.dtos;

public class NotificationDTO {
    private String notificationMessage;
    private String date;
    private String profilePicture;

    private String name;

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture=profilePicture;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public String getDate() {
        return date;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getName() {
        return name;
    }
}
