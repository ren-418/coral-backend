package com.coral.backend.repositories;

import com.coral.backend.entities.Area;
import com.coral.backend.entities.EnterpriseUser;
import com.coral.backend.entities.InvestorUser;
import com.coral.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnterpriseUserRepository extends JpaRepository<EnterpriseUser, String> {
  List<EnterpriseUser> findAllByEnterpriseTypeAndAreasAndLocation(String enterpriseType, Area area, String location);

  List<EnterpriseUser> findAllByEnterpriseTypeAndAreas(String enterpriseType, Area area);

  List<EnterpriseUser> findAllByEnterpriseTypeAndLocation(String enterpriseType, String location);

  List<EnterpriseUser> findAllByEnterpriseType(String enterpriseType);

  List<EnterpriseUser> findAllByAreasAndLocation(Area area, String location);

  List<EnterpriseUser> findAllByAreas(Area area);

  List<EnterpriseUser> findAllByLocation(String location);

  List<EnterpriseUser> findAll();
}
