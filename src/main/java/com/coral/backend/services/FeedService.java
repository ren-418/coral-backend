package com.coral.backend.services;

import com.coral.backend.dtos.*;
import com.coral.backend.entities.Area;
import com.coral.backend.entities.EnterpriseUser;
import com.coral.backend.entities.InvestorUser;
import com.coral.backend.repositories.EnterpriseUserRepository;
import com.coral.backend.repositories.InvestorUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FeedService {
  @Autowired
  private AuthService authService;

  @Autowired
  private EnterpriseUserRepository enterpriseUserRepository;

  @Autowired
  private InvestorUserRepository investorUserRepository;

  public ResponseEntity<Object> getRecommendedEnterprises(CheckSessionDTO sessionDTO) {
    InvestorUser user = (InvestorUser) authService.checkAuth(sessionDTO.getSessionToken());
    List<Area> userAreas = user.getAreas();
    String location = user.getLocation();
    List<EnterpriseDTO> sameAreasEnterprisesDTO = new ArrayList<>();
    List<EnterpriseUser> sameLocationEnterprises = enterpriseUserRepository.findAllByLocation(location);
    List<EnterpriseDTO> sameLocationEnterprisesDTO = new ArrayList<>();

    for (EnterpriseUser enterprise : sameLocationEnterprises) {
      sameLocationEnterprisesDTO.add(enterprise.toDTO());
    }

    List<Long> sameAreasEnterprisesIds = new ArrayList<>();

    for (Area area : userAreas) {
      List<EnterpriseUser> matchingAreaEnterprises = enterpriseUserRepository.findAllByAreas(area);
      for (EnterpriseUser enterprise : matchingAreaEnterprises) {
        if(!sameAreasEnterprisesIds.contains(enterprise.getUserId())){
          sameAreasEnterprisesIds.add(enterprise.getUserId());
          sameAreasEnterprisesDTO.add(enterprise.toDTO());
        }
      }
    }

    RecommendedEnterprisesDTO frontDataPackage = new RecommendedEnterprisesDTO();
    frontDataPackage.setSameAreas(sameAreasEnterprisesDTO);
    frontDataPackage.setSameLocation(sameLocationEnterprisesDTO);
    return new ResponseEntity<>(frontDataPackage, HttpStatus.OK);
  }

  public ResponseEntity<Object> getRecommendedInvestors(CheckSessionDTO sessionDTO) {
    EnterpriseUser user = (EnterpriseUser) authService.checkAuth(sessionDTO.getSessionToken());
    List<Area> userAreas = user.getAreas();
    String location = user.getLocation();
    List<InvestorDTO> sameAreasInvestorsDTO = new ArrayList<>();
    List<InvestorUser> sameLocationInvestors = investorUserRepository.findAllByLocation(location);
    List<InvestorDTO> sameLocationInvestorsDTO = new ArrayList<>();

    for (InvestorUser investor : sameLocationInvestors) {
      sameLocationInvestorsDTO.add(investor.toDTO());
    }

    List<Long> sameAreasInvestorsIds = new ArrayList<>();

    for (Area area : userAreas) {
      List<InvestorUser> matchingAreaInvestors = investorUserRepository.findAllByAreas(area);
      for (InvestorUser investor : matchingAreaInvestors) {
        if(!sameAreasInvestorsIds.contains(investor.getUserId())){
          sameAreasInvestorsIds.add(investor.getUserId());
          sameAreasInvestorsDTO.add(investor.toDTO());
        }
      }
    }

    RecommendedInvestorsDTO frontDataPackage = new RecommendedInvestorsDTO();
    frontDataPackage.setSameAreas(sameAreasInvestorsDTO);
    frontDataPackage.setSameLocation(sameLocationInvestorsDTO);
    return new ResponseEntity<>(frontDataPackage, HttpStatus.OK);
  }
}
