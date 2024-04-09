package com.coral.backend.dtos;

import com.coral.backend.entities.Area;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

public class InvestorDTO {
    private int investor_type;
    private String investment_criteria;
    private int range_min;
    private int range_max;
    private long userId;
    private boolean firstLogin;
    private String name;
    private String email;
    private String description;
    private String location;
    private Date initial_date;
    private List<Area> areas;
    private String userType;

    //Setters
    public void setInvestorType(int investor_type) {
        this.investor_type = investor_type;
    }

    public void setInvestmentCriteria(String investment_criteria) {
        this.investment_criteria = investment_criteria;
    }

    public void setRangeMin(int range_min) {
        this.range_min = range_min;
    }

    public void setRangeMax(int range_max) {
        this.range_max = range_max;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    //Getters
    public int getInvestorType() {
        return investor_type;
    }

    public String getInvestmentCriteria() {
        return investment_criteria;
    }

    public int getRangeMin() {
        return range_min;
    }

    public int getRangeMax() {
        return range_max;
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

    public void setInitialDate(Date initial_date) {
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
