package com.coral.backend.dtos;

import java.time.LocalDate;

public class RegisterDTO {
    private String email;
    private String password;
    private String accountType;

    //Getters

    public String getEmail() {
        return email.toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public String getAccountType() {
        return accountType;
    }

    //Setters

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType.toLowerCase();
    }
}
