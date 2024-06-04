package com.coral.backend.entities;

import jakarta.persistence.*;
import org.apache.logging.log4j.message.Message;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //TABLE_PER_CLASS o JOINED
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(nullable = false)
    private boolean firstLogin;

    private String name;

    private byte[] profileImage;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
    private String description;
    private String location;
    private LocalDate initial_date;
    private String userType;

    public User(){}

    @ManyToMany(cascade=CascadeType.ALL)
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setInitialDate(LocalDate initial_date) {
        this.initial_date = initial_date;
    }

    public void setAreas(List<Area> area) {
        this.areas = area;
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

    public String getPassword() {
        return password;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public LocalDate getInitialDate() {
        return initial_date;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public boolean getFirstLogin() {
        return firstLogin;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }
}
