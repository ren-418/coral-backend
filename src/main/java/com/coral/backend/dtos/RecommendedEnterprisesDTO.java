package com.coral.backend.dtos;

import com.coral.backend.entities.EnterpriseUser;

import java.util.ArrayList;
import java.util.List;

public class RecommendedEnterprisesDTO {
  private List<EnterpriseDTO> sameAreas;
  private List<EnterpriseDTO> sameLocation;

  public List<EnterpriseDTO> getSameAreas() {
    return sameAreas;
  }

  public void setSameAreas(List<EnterpriseDTO> sameAreas){
    this.sameAreas = sameAreas;
  }

  public void setSameLocation(List<EnterpriseDTO> sameLocation){
    this.sameLocation = sameLocation;
  }
  public List<EnterpriseDTO> getSameLocation() {
    return sameLocation;
  }
}
