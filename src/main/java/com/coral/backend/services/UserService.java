package com.coral.backend.services;

import com.coral.backend.dtos.InvestorDTO;
import com.coral.backend.entities.InvestorUser;
import com.coral.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service

public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    public ResponseEntity<Object> createInvestorProfile(InvestorDTO requestBody){
        InvestorUser user = (InvestorUser) authService.checkAuth(requestBody.getSessionToken());
        if(user == null){
            return new ResponseEntity<>("You don't have auth permision", HttpStatus.UNAUTHORIZED);
        }

        user.setProfileImage(requestBody.getProfileImage());
        user.setName(requestBody.getName());
        user.setDescription(requestBody.getDescription());
        user.setLocation(requestBody.getLocation());
        user.setInvestorType(requestBody.getInvestorType());
        user.setRangeMax(requestBody.getRangeMax());
        user.setRangeMin(requestBody.getRangeMin());
        user.setInvestmentCriteria(requestBody.getInvestmentCriteria());
        user.setFirstLogin(false);
        userRepository.save(user);
        return new ResponseEntity<>("Profile created successfully", HttpStatus.OK);
    }

}
