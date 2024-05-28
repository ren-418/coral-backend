package com.coral.backend.services;

import com.coral.backend.dtos.*;
import com.coral.backend.entities.Area;
import com.coral.backend.entities.EnterpriseUser;
import com.coral.backend.entities.InvestorUser;
import com.coral.backend.entities.User;
import com.coral.backend.repositories.AreaRepository;
import com.coral.backend.repositories.EnterpriseUserRepository;
import com.coral.backend.repositories.InvestorUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.coral.backend.services.UserService.decodeImage;

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
      EnterpriseDTO enterpriseDTO = new EnterpriseDTO();
      enterpriseDTO.setUserId(enterprise.getUserId());
      enterpriseDTO.setName(enterprise.getName());
      enterpriseDTO.setDescription(enterprise.getDescription());
      enterpriseDTO.setProfileImage(decodeImage(enterprise.getProfileImage()));
      List<String> areaNames = new ArrayList<>();
      for (Area a : enterprise.getAreas()){
        areaNames.add(a.getName());
      }
      enterpriseDTO.setAreas(areaNames);
      enterpriseDTO.setLocation(enterprise.getLocation());
      enterpriseDTO.setEnterpriseType(enterprise.getEnterpriseType());
      enterpriseDTO.setGoal(enterprise.getGoal());
      enterpriseDTO.setMinimumInvestment(enterprise.getMinimumInvestment());
      enterpriseDTO.setTotalProfitReturn(enterprise.getTotalProfitReturn());
      enterpriseDTO.setTotalCollected(enterprise.getTotalCollected());
      sameLocationEnterprisesDTO.add(enterpriseDTO);
    }

    List<Long> sameAreasEnterprisesIds = new ArrayList<>();

    for (Area area : userAreas) {
      List<EnterpriseUser> matchingAreaEnterprises = enterpriseUserRepository.findAllByAreas(area);
      for (EnterpriseUser enterprise : matchingAreaEnterprises) {
        EnterpriseDTO enterpriseDTO = new EnterpriseDTO();
        enterpriseDTO.setName(enterprise.getName());
        enterpriseDTO.setUserId(enterprise.getUserId());
        enterpriseDTO.setDescription(enterprise.getDescription());
        enterpriseDTO.setProfileImage(decodeImage(enterprise.getProfileImage()));
        List<String> areaNames = new ArrayList<>();
        for (Area a : enterprise.getAreas()){
          areaNames.add(a.getName());
        }
        enterpriseDTO.setAreas(areaNames);
        enterpriseDTO.setLocation(enterprise.getLocation());
        enterpriseDTO.setEnterpriseType(enterprise.getEnterpriseType());
        enterpriseDTO.setGoal(enterprise.getGoal());
        enterpriseDTO.setMinimumInvestment(enterprise.getMinimumInvestment());
        enterpriseDTO.setTotalProfitReturn(enterprise.getTotalProfitReturn());
        enterpriseDTO.setTotalCollected(enterprise.getTotalCollected());
        if(!sameAreasEnterprisesIds.contains(enterprise.getUserId())){
          sameAreasEnterprisesIds.add(enterprise.getUserId());
          sameAreasEnterprisesDTO.add(enterpriseDTO);
        }
      }

    }

    RecommendedEnterprisesDTO frontDataPackage = new RecommendedEnterprisesDTO();
    frontDataPackage.setSameAreas(sameAreasEnterprisesDTO);
    frontDataPackage.setSameLocation(sameLocationEnterprisesDTO);
    return new ResponseEntity<>(frontDataPackage, HttpStatus.OK);
  }

  public ResponseEntity<Object> getRecommendedInvestors(CheckSessionDTO sessionDTO) {
    InvestorUser user = (InvestorUser) authService.checkAuth(sessionDTO.getSessionToken());
    List<Area> userAreas = user.getAreas();
    String location = user.getLocation();
    List<InvestorDTO> sameAreasInvestorsDTO = new ArrayList<>();
    List<InvestorUser> sameLocationInvestors = investorUserRepository.findAllByLocation(location);
    List<InvestorDTO> sameLocationInvestorsDTO = new ArrayList<>();

    for (InvestorUser investor : sameLocationInvestors) {
      InvestorDTO investorDTO = new InvestorDTO();
      investorDTO.setUserId(investor.getUserId());
      investorDTO.setName(investor.getName());
      investorDTO.setInvestorType(investor.getInvestorType());
      investorDTO.setInvestmentCriteria(investor.getInvestmentCriteria());
      investorDTO.setRangeMin(investor.getRangeMin());
      investorDTO.setRangeMax(investor.getRangeMax());
      sameLocationInvestorsDTO.add(investorDTO);
    }

    List<Long> sameAreasInvestorsIds = new ArrayList<>();

    for (Area area : userAreas) {
      List<InvestorUser> matchingAreaInvestors = investorUserRepository.findAllByAreas(area);
      for (InvestorUser investor : matchingAreaInvestors) {
        InvestorDTO investorDTO = new InvestorDTO();
        investorDTO.setUserId(investor.getUserId());
        investorDTO.setName(investor.getName());
        investorDTO.setInvestorType(investor.getInvestorType());
        investorDTO.setInvestmentCriteria(investor.getInvestmentCriteria());
        investorDTO.setRangeMin(investor.getRangeMin());
        investorDTO.setRangeMax(investor.getRangeMax());
        if(!sameAreasInvestorsIds.contains(investor.getUserId())){
          sameAreasInvestorsIds.add(investor.getUserId());
          sameAreasInvestorsDTO.add(investorDTO);
        }
      }

    }

    RecommendedInvestorsDTO frontDataPackage = new RecommendedInvestorsDTO();
    frontDataPackage.setSameAreas(sameAreasInvestorsDTO);
    frontDataPackage.setSameLocation(sameLocationInvestorsDTO);
    return new ResponseEntity<>(frontDataPackage, HttpStatus.OK);
  }
}
