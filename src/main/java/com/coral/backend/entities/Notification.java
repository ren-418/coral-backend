package com.coral.backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long notificationId;

    @ManyToOne
    @JoinColumn(name = "investor_id")
    private InvestorUser investor;
    @ManyToOne
    @JoinColumn(name = "enterprise_id")
    private EnterpriseUser enterprise;

    public InvestorUser getInvestor() {
        return investor;
    }

    public EnterpriseUser getEnterprise() {
        return enterprise;
    }

    public void setInvestor(InvestorUser investor) {
        this.investor = investor;
    }

    public void setEnterprise(EnterpriseUser enterprise) {
        this.enterprise = enterprise;
    }

    @Column(name = "message")
    private String message;

    @Column(name = "time_stamp")
    private String timeStamp;

    @Column(name = "read")
    private boolean read;

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
