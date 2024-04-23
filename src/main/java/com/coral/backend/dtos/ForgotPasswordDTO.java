package com.coral.backend.dtos;

public class ForgotPasswordDTO {
    private String token;
    private String email;

    private String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setPassword(String password) {this.password = password;}

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {return password;}
}
