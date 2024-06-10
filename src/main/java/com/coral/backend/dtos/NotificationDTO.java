package com.coral.backend.dtos;

public class NotificationDTO {
    private String notificationMessage;
    private String date;
    private InvestorDTO investor;

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setInvestor(InvestorDTO investor) {
        this.investor = investor;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public String getDate() {
        return date;
    }

    public InvestorDTO getInvestor() {
        return investor;
    }
}
