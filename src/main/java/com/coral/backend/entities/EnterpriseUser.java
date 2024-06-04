package com.coral.backend.entities;

import jakarta.persistence.*;

import java.util.List;


@Entity
public class EnterpriseUser extends User {
    private String enterpriseType;
    private int goal;
    private int minimumInvestment;
    private int totalProfitReturn;
    private int totalCollected;
    @ManyToMany(mappedBy = "enterprises")
    private List<InvestorUser> investors;

    @ManyToMany(mappedBy = "chatsWithEnterprises")
    private List<InvestorUser> chatsWithInvestors;

    // Setters
    public void setInvestors(List<InvestorUser> investors) {
        this.investors = investors;
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
    public List<InvestorUser> getInvestors() {
        return investors;
    }
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
}
