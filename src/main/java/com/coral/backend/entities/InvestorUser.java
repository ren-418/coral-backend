package com.coral.backend.entities;

import com.coral.backend.dtos.EnterpriseDTO;
import com.coral.backend.dtos.InvestorDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class InvestorUser extends User {

    @Column(insertable=false, updatable=false)
    private String userType = "investor";

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

    //Setters

    public void setEnterprises(List<EnterpriseUser> enterprises) {
        this.enterprises = enterprises;
    }
    public void setUserType(String userType) {
        this.userType = userType;
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

    public String getUserType() {
        return userType;
    }

    public InvestorDTO toDTO(){
        InvestorDTO investorDTO = new InvestorDTO();
        investorDTO.setInvestorType(investorType);
        investorDTO.setUserId(getUserId());
        List<String> areaNames = new ArrayList<>();
        for (Area area : getAreas()){
            areaNames.add(area.getName());
        }
        investorDTO.setAreas(areaNames);
        investorDTO.setDescription(getDescription());
        investorDTO.setInvestmentCriteria(getInvestmentCriteria());
        investorDTO.setLocation(getLocation());
        investorDTO.setName(getName());
        investorDTO.setProfilePicture(getProfileImageString());
        investorDTO.setRangeMax(getRangeMax());
        investorDTO.setRangeMin(getRangeMin());
        return investorDTO;
    }
}
