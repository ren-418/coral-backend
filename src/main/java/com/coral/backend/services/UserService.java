package com.coral.backend.services;

import com.coral.backend.dtos.EnterpriseDTO;
import com.coral.backend.dtos.InvestorDTO;
import com.coral.backend.entities.Area;
import com.coral.backend.entities.EnterpriseUser;
import com.coral.backend.entities.InvestorUser;
import com.coral.backend.repositories.AreaRepository;

import com.coral.backend.repositories.ResetTokenRepository;
import com.coral.backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service

public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private AreaRepository areaRepository;
  
    @Autowired
    private ResetTokenRepository passwordTokenRepository;
  
    @Transactional
    public ResponseEntity<Object> createInvestorProfile(InvestorDTO requestBody){
        InvestorUser user = (InvestorUser) authService.checkAuth(requestBody.getSessionToken());
        System.out.println(requestBody.getAreas());
        if(user == null){
            return new ResponseEntity<>("You don't have auth permision", HttpStatus.UNAUTHORIZED);
        }

        List<Area> areaList = new ArrayList<>();
        for (String area : requestBody.getAreas()) {
            areaList.add(areaRepository.findAreaByName(area).get());
        }
        user.setAreas(areaList);
        user.setInitialDate(getDate());
        user.setProfileImage(encodeImage(requestBody.getProfilePicture()));
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

    @Transactional
    public ResponseEntity<Object> createEnterpriseProfile(EnterpriseDTO requestBody){
        EnterpriseUser user = (EnterpriseUser) authService.checkAuth(requestBody.getSessionToken());
        if(user == null){
            return new ResponseEntity<>("You don't have auth permision", HttpStatus.UNAUTHORIZED);
        }
        List<Area> areaList = new ArrayList<>();
        for (String area : requestBody.getAreas()) {
            areaList.add(areaRepository.findAreaByName(area).get());
        }
        user.setAreas(areaList);
        user.setInitialDate(getDate());
        user.setProfileImage(encodeImage(requestBody.getProfileImage()));
        user.setName(requestBody.getName());
        user.setDescription(requestBody.getDescription());
        user.setLocation(requestBody.getLocation());
        if (Objects.equals(requestBody.getEnterpriseType(), "Community")){
            user.setEnterpriseType("Community");
            user.setGoal(requestBody.getGoal());
            user.setMinimumInvestment(requestBody.getMinimumInvestment());
            user.setTotalProfitReturn(requestBody.getTotalProfitReturn());
        } else {
            user.setEnterpriseType("Custom");
        }
        user.setFirstLogin(false);
        userRepository.save(user);
        return new ResponseEntity<>("Profile created successfully", HttpStatus.OK);
    }

    public byte[] encodeImage(String base64){
        String encodedString = Base64.getEncoder().encodeToString(base64.getBytes());
        return java.util.Base64.getDecoder().decode(encodedString);
    }

    public static String decodeImage(byte[] byteArray) {
        return new String(byteArray);
    }

    public LocalDate getDate(){
        return LocalDate.now();
    }
}
