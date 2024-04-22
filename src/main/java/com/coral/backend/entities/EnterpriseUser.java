package com.coral.backend.entities;

import jakarta.persistence.*;


@Entity
public class EnterpriseUser extends User {
    String investmentType;
    int goal;
    int minimumInvestment;
    int totalProfitReturn;

    @Column(insertable=false, updatable=false)
    private String userType = "enterprise";

    // Setters
    public void setGoal(int goal) {
        this.goal = goal;
    }
    public void setMinimumInvestment(int minimumInvestment) {
        this.minimumInvestment = minimumInvestment;
    }
    public void setTotalProfitReturn(int totalProfitReturn) {
        this.totalProfitReturn = totalProfitReturn;
    }
    public void setInvestmentType(String investmentType) {
        this.investmentType = investmentType;
    }

    // Getters
    public String getInvestmentType() {
        return investmentType;
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
