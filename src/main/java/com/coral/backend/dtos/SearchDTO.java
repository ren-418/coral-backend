package com.coral.backend.dtos;

import com.coral.backend.entities.Area;

import java.util.List;

public class SearchDTO {
    private List<Area> interests;
    private String location;
    private Integer investorType;

    //Setters
    public void setInterests(List<Area> interests) {
        this.interests = interests;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setInvestorType(Integer investorType) {
        this.investorType = investorType;
    }
    //Getters
    public List<Area> getInterests() {
        return interests;
    }
    public String getLocation() {
        return location;
    }
    public Integer getInvestorType() {
        return investorType;
    }
}
