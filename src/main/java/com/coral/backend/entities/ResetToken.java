package com.coral.backend.entities;
import jakarta.persistence.*;

@Entity
public class ResetToken {

    @Id
    private String token;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    public void setToken(String token) {
        this.token = token;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getToken() {
        return token;
    }
    public User getUser() {
        return user;
    }

}
