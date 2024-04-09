package com.coral.backend.dtos;

import com.coral.backend.entities.Area;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

public class EnterpriseDTO {
    private String userType;
    private long userId;
    private boolean firstLogin;
    private String name;
    private String email;
    private String description;
    private String location;
    private Date initial_date;
    private List<Area> areas;

    //Setters
    public void setUserId(long user_id) {
        this.userId = user_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setInitialDate(Date initial_date) {
        this.initial_date = initial_date;
    }

    public void setAreas(List<Area> area) {
        this.areas = area;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    //Getters
    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public Date getInitialDate() {
        return initial_date;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public boolean getFirstLogin() {
        return firstLogin;
    }
    public String getUserType() {
        return userType;
    }

}
