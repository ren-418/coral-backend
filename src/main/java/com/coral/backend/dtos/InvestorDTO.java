package com.coral.backend.dtos;

import com.coral.backend.entities.Area;
import com.coral.backend.entities.EnterpriseUser;

import java.util.Date;
import java.util.List;

public class InvestorDTO {
    private String profilePicture;
    private int investorType;
    private String investmentCriteria;
    private int rangeMin;
    private int rangeMax;
    private long userId;
    private boolean firstLogin;
    private String name;
    private String email;
    private String description;
    private String location;
    private List<String> areas;
    private String userType;
    private String sessionToken;
    private List<EnterpriseDTO> enterprises;

    //Setters
    public void setEnterprises(List<EnterpriseDTO> enterprises) {
        this.enterprises = enterprises;
    }
    public void setProfilePicture(String base64) {
        this.profilePicture = base64;
    }
    public void setInvestorType(int investor_type) {
        this.investorType = investor_type;
    }

    public void setInvestmentCriteria(String investment_criteria) {
        this.investmentCriteria = investment_criteria;
    }

    public void setRangeMin(int range_min) {
        this.rangeMin = range_min;
    }

    public void setRangeMax(int range_max) {
        this.rangeMax = range_max;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    //Getters
    public int getInvestorType() {
        return investorType;
    }

    public String getInvestmentCriteria() {
        return investmentCriteria;
    }

    public int getRangeMin() {
        return rangeMin;
    }

    public int getRangeMax() {
        return rangeMax;
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

    public void setAreas(List<String> area) {
        this.areas = area;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    //Getters
    public String getProfilePicture(){
        return profilePicture;
    }
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

    public List<String> getAreas() {
        return areas;
    }

    public boolean getFirstLogin() {
        return firstLogin;
    }

    public String getUserType() {
        return userType;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public List<EnterpriseDTO> getEnterprises() {
        return enterprises;
    }
}
