package com.coral.backend.dtos;

import java.util.List;

public class PdfDTO {

    private List<InvestmentInfoDTO> investmentInfo;

    //Setters

    public void setInvestmentInfo(List<InvestmentInfoDTO> investmentInfo) {
        this.investmentInfo = investmentInfo;
    }

    //Getters

    public List<InvestmentInfoDTO> getInvestmentInfo() {
        return investmentInfo;
    }
}
