package com.coral.backend.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class InvestorUser extends User {

    private int investorType;
    private String investmentCriteria;
    private int rangeMin;
    private int rangeMax;
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
        name = "investments",
        joinColumns = @JoinColumn(name = "investor_id"),
        inverseJoinColumns = @JoinColumn(name = "enterprise_id")
    )
    private List<EnterpriseUser> enterprises;
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
        name = "chat_rooms",
        joinColumns = @JoinColumn(name = "investor_id"),
        inverseJoinColumns = @JoinColumn(name = "enterprise_id")
    )
    private List<EnterpriseUser> chatsWithEnterprises;

    //Setters

    public void setEnterprises(List<EnterpriseUser> enterprises) {
        this.enterprises = enterprises;
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

    //Getters
    public List<EnterpriseUser> getEnterprises() {
        return enterprises;
    }
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
}
