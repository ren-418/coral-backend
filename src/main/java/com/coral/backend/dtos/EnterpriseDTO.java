package com.coral.backend.dtos;

import java.util.List;

public class EnterpriseDTO {
    private String profileImage;
    private String userType;
    private long userId;
    private boolean firstLogin;
    private String name;
    private String email;
    private String description;
    private String location;
    private List<String> areas;
    private String sessionToken;
    String enterpriseType;
    int goal;
    int minimumInvestment;
    int totalProfitReturn;

    //Setters
    public void setSessionToken(String sessionToken){
        this.sessionToken=sessionToken;
    }
    public void setProfileImage(String base64) {
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

    public void setAreas(List<String> area) {
        this.areas = area;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setEnterpriseType(String enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public void setMinimumInvestment(int minimumInvestment) {
        this.minimumInvestment = minimumInvestment;
    }

    public void setTotalProfitReturn(int totalProfitReturn) {
        this.totalProfitReturn = totalProfitReturn;
    }



    //Getters
    public int getMinimumInvestment() {
        return minimumInvestment;
    }
    public int getTotalProfitReturn() {
        return totalProfitReturn;
    }
    public int getGoal() {
        return goal;
    }
    public String getEnterpriseType() {
        return enterpriseType;
    }
    public String getSessionToken() {
        return sessionToken;
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
    public String getProfileImage(){
        return profileImage;
    }

}
