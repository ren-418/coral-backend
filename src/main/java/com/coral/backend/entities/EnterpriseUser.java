package com.coral.backend.entities;

import jakarta.persistence.*;


@Entity
public class EnterpriseUser extends User {
    String enterpriseType;
    int goal;
    int minimumInvestment;
    int totalProfitReturn;
    int totalCollected;

    @Column(insertable=false, updatable=false)
    private String userType = "enterprise";

    // Setters
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public void setTotalCollected(int totalCollected) {
        this.totalCollected = totalCollected;
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
    public void setEnterpriseType(String enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    // Getters
    public int getTotalCollected() {
        return totalCollected;
    }
    public String getEnterpriseType() {
        return enterpriseType;
    }
    public int getGoal() {
        return goal;
    }
    public int getTotalProfitReturn() {
        return totalProfitReturn;
    }
    public int getMinimumInvestment() {
        return minimumInvestment;
    }
    public String getUserType() {
        return userType;
    }
}
