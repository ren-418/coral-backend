package com.coral.backend.repositories;

import com.coral.backend.entities.Area;
import com.coral.backend.entities.InvestorUser;
import com.coral.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvestorUserRepository extends JpaRepository<InvestorUser, String> {
    List<User> findAllByInvestorTypeAndAreasAndLocation(int userType, Area area, String location);

    List<User> findAllByInvestorTypeAndAreas(int investorType, Area area);

    List<User
            > findAllByInvestorTypeAndLocation(int investorType, String location);

    List<User> findAllByInvestorType(int investorType);
}
