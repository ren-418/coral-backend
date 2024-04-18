package com.coral.backend.dtos;

import com.coral.backend.entities.Area;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

public class EnterpriseDTO {
    private byte[] profileImage;
    private String userType;
    private long userId;
    private boolean firstLogin;
    private String name;
    private String email;
    private String description;
    private String location;
    private List<Area> areas;

    //Setters
    public void setProfileImage(byte[] base64) {
        this.profileImage = base64;
    }
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

    public List<Area> getAreas() {
        return areas;
    }

    public boolean getFirstLogin() {
        return firstLogin;
    }
    public String getUserType() {
        return userType;
    }
    public byte[] getProfileImage(){
        return profileImage;
    }

}
