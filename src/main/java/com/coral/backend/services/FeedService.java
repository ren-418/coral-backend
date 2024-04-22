package com.coral.backend.services;

import com.coral.backend.dtos.CheckSessionDTO;
import com.coral.backend.dtos.InvestorDTO;
import com.coral.backend.dtos.RecommendedEnterprisesDTO;
import com.coral.backend.entities.Area;
import com.coral.backend.entities.EnterpriseUser;
import com.coral.backend.entities.InvestorUser;
import com.coral.backend.entities.User;
import com.coral.backend.repositories.AreaRepository;
import com.coral.backend.repositories.EnterpriseUserRepository;
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

  public ResponseEntity<Object> getRecommendedEnterprises(CheckSessionDTO sessionDTO) {
    System.out.println("entro");
    InvestorUser user = (InvestorUser) authService.checkAuth(sessionDTO.getSessionToken());
    List<Area> userAreas = user.getAreas();
    String location = user.getLocation();
    List<EnterpriseUser> sameAreasEnterprises = new ArrayList<>();
    List<EnterpriseUser> sameLocationEnterprises = enterpriseUserRepository.findAllByLocation(location);

    for (Area area : userAreas) {
      List<EnterpriseUser> matchingAreaEnterprises = enterpriseUserRepository.findAllByAreas(area);
      sameAreasEnterprises.addAll(matchingAreaEnterprises);
    }

    RecommendedEnterprisesDTO frontDataPackage = new RecommendedEnterprisesDTO();
    frontDataPackage.setSameAreas(sameAreasEnterprises);
    frontDataPackage.setSameLocation(sameLocationEnterprises);
    System.out.println("preReturn");
    return new ResponseEntity<>(frontDataPackage, HttpStatus.OK);
  }
}
