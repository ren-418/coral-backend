package com.coral.backend.dtos;

import java.util.List;

public class SearchDTO {
    private List<String> areas;
    private List<String> locations;
    private Integer investorType;
    private String userName;
    private String enterpriseType;
    private long userId;


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
    public void setUserName(String userName) {
        this.userName = userName;
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
    public String getUserName() {
        return userName;
    }
    public String getEnterpriseType() {
        return enterpriseType;
    }
}
