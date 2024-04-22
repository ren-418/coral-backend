package com.coral.backend.dtos;

import java.util.List;

public class SearchDTO {
    private List<String> areas;
    private List<String> locations;
    private Integer investorType;

    //Setters
    public void setAreas(List<String> areas) {
        this.areas = areas;
    }
    public void setLocations(List<String> locations) {
        this.locations = locations;
    }
    public void setInvestorType(Integer investorType) {
        this.investorType = investorType;
    }
    //Getters
    public List<String> getAreas() {
        return areas;
    }
    public List<String> getLocations() {
        return locations;
    }
    public Integer getInvestorType() {
        return investorType;
    }
}
