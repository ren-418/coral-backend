package com.coral.backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long notificationId;

    @ManyToOne
    @JoinColumn(name = "from_id")
    private User from;
    @ManyToOne
    @JoinColumn(name = "to_id")
    private User to;

    public User getFrom() {return from;}

    public User getTo() {
        return to;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public void setTo(User to) {
        this.to = to;
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
