package com.coral.backend.entities;

import jakarta.persistence.*;

@Entity
public class InvestorUser extends User {

    @Column(insertable=false, updatable=false)
    private String userType = "investor";

    private int investorType;
    private String investmentCriteria;
    private int rangeMin;
    private int rangeMax;

    //Setters
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

    public String getUserType() {
        return userType;
    }
}
