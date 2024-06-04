package com.coral.backend.entities;

import com.coral.backend.dtos.EnterpriseDTO;
import com.coral.backend.dtos.InvestorDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
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

    public String getUserType() {
        return userType;
    }

    public EnterpriseDTO toDTO(){
        EnterpriseDTO enterpriseDTO = new EnterpriseDTO();
        enterpriseDTO.setEnterpriseType(getEnterpriseType());
        enterpriseDTO.setGoal(getGoal());
        enterpriseDTO.setMinimumInvestment(getMinimumInvestment());
        enterpriseDTO.setTotalProfitReturn(getTotalProfitReturn());
        enterpriseDTO.setTotalCollected(getTotalCollected());
        enterpriseDTO.setUserId(getUserId());
        enterpriseDTO.setName(getName());
        enterpriseDTO.setProfileImage(getProfileImageString());
        enterpriseDTO.setUserId(getUserId());
        enterpriseDTO.setLocation(getLocation());
        enterpriseDTO.setFirstLogin(getFirstLogin());
        enterpriseDTO.setUserType(getUserType());
        List<String> areaNames = new ArrayList<>();
        for (Area area : getAreas()){
            areaNames.add(area.getName());
        }
        enterpriseDTO.setAreas(areaNames);
        enterpriseDTO.setDescription(getDescription());
        List<InvestorDTO> investorsDTO = new ArrayList<>();
        for (InvestorUser investor : getInvestors()){
            investorsDTO.add(investor.toDTOWithoutEnterprises());
        }
        enterpriseDTO.setInvestors(investorsDTO);
        return enterpriseDTO;
    }

    public EnterpriseDTO toDTOWithoutInvestors() {
        EnterpriseDTO enterpriseDTO = new EnterpriseDTO();
        enterpriseDTO.setEnterpriseType(getEnterpriseType());
        enterpriseDTO.setGoal(getGoal());
        enterpriseDTO.setMinimumInvestment(getMinimumInvestment());
        enterpriseDTO.setTotalProfitReturn(getTotalProfitReturn());
        enterpriseDTO.setTotalCollected(getTotalCollected());
        enterpriseDTO.setUserId(getUserId());
        enterpriseDTO.setName(getName());
        enterpriseDTO.setProfileImage(getProfileImageString());
        enterpriseDTO.setUserId(getUserId());
        enterpriseDTO.setLocation(getLocation());
        enterpriseDTO.setFirstLogin(getFirstLogin());
        enterpriseDTO.setUserType(getUserType());
        List<String> areaNames = new ArrayList<>();
        for (Area area : getAreas()){
            areaNames.add(area.getName());
        }
        enterpriseDTO.setAreas(areaNames);
        enterpriseDTO.setDescription(getDescription());
        return enterpriseDTO;
    }

}
