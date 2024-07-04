package com.coral.backend.dtos;

public class ActivityDTO {

    private String sessionToken;

    private long userId;

    private String from;

    private String to;

    //Setters
    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    //Getters

    public long getUserId() {
        return userId;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}
