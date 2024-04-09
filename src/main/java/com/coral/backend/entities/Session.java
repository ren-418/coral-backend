package com.coral.backend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String sessionToken;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    public Session(){}

    //getters
    public String getSessionToken() {
        return sessionToken;
    }

    public User getUser() {
        return user;
    }

    //setters
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
