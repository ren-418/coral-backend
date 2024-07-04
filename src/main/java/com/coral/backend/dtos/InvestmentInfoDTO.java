package com.coral.backend.dtos;

public class InvestmentInfoDTO {

    private String name;

    private Integer amount;

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    //Getters
    public String getName() {
        return name;
    }

    public Integer getAmount() {
        return amount;
    }
}
