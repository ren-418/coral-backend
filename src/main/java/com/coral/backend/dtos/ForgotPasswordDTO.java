package com.coral.backend.dtos;

import com.coral.backend.entities.User;

public class ForgotPasswordDTO {
    private String token;
    private String userEmail;

    private String password;

    public void setUserEmail(String userEmail) {
        this.userEmail=userEmail;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setPassword(String password) {this.password = password;}

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return userEmail;
    }

    public String getPassword() {return password;}
}
