package com.coral.backend.dtos;

import java.util.List;

public class RecommendedInvestorsDTO {
    private List<InvestorDTO> sameAreas;
    private List<InvestorDTO> sameLocation;

    public List<InvestorDTO> getSameAreas() {
        return sameAreas;
    }

    public void setSameAreas(List<InvestorDTO> sameAreas){
        this.sameAreas = sameAreas;
    }

    public void setSameLocation(List<InvestorDTO> sameLocation){
        this.sameLocation = sameLocation;
    }
    public List<InvestorDTO> getSameLocation() {
        return sameLocation;
    }
}
