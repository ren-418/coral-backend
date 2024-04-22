package com.coral.backend.dtos;

import com.coral.backend.entities.EnterpriseUser;

import java.util.ArrayList;
import java.util.List;

public class RecommendedEnterprisesDTO {
  private List<EnterpriseUser> sameAreas;
  private List<EnterpriseUser> sameLocation;

  public List<EnterpriseUser> getSameAreas() {
    return sameAreas;
  }

  public void setSameAreas(List<EnterpriseUser> sameAreas){
    this.sameAreas = sameAreas;
  }

  public void setSameLocation(List<EnterpriseUser> sameLocation){
    this.sameLocation = sameLocation;
  }
  public List<EnterpriseUser> getSameLocation() {
    return sameLocation;
  }
}
