package com.coral.backend.entities;

import jakarta.persistence.*;

@Entity
public class InvestorUser extends User {

    @Column(insertable=false, updatable=false)
    private String userType = "investor";

    private int investor_type;
    private String investment_criteria;
    private int range_min;
    private int range_max;

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

    public String getUserType() {
        return userType;
    }
}
