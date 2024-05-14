package com.coral.backend.services;

import com.coral.backend.dtos.EnterpriseDTO;
import com.coral.backend.dtos.InvestDTO;
import com.coral.backend.dtos.InvestorDTO;
import com.coral.backend.entities.*;
import com.coral.backend.repositories.*;

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

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private EnterpriseUserRepository enterpriseUserRepository;

    @Autowired
    private InvestmentRepository investmentRepository;
  
    @Transactional
    public ResponseEntity<Object> createInvestorProfile(InvestorDTO requestBody){
        InvestorUser user = (InvestorUser) authService.checkAuth(requestBody.getSessionToken());
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
        System.out.println(requestBody.getEnterpriseType());
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

    public ResponseEntity<Object> getEnterpriseProfile(EnterpriseDTO requestBody) {
        Optional<Session> optionalSession = sessionRepository.findSessionBySessionToken(requestBody.getSessionToken());

        if (optionalSession.isEmpty()) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }

        EnterpriseUser enterpriseUser = enterpriseUserRepository.findEnterpriseUserByUserId(requestBody.getUserId());

        EnterpriseDTO toReturnDTO = getEnterpriseDTO(enterpriseUser);

        return new ResponseEntity<>(toReturnDTO, HttpStatus.OK);
    }

    private static EnterpriseDTO getEnterpriseDTO(EnterpriseUser enterpriseUser) {
        EnterpriseDTO toReturnDTO = new EnterpriseDTO();

        toReturnDTO.setEnterpriseType(enterpriseUser.getEnterpriseType());
        toReturnDTO.setName(enterpriseUser.getName());
        toReturnDTO.setProfileImage(decodeImage(enterpriseUser.getProfileImage()));
        toReturnDTO.setLocation(enterpriseUser.getLocation());
        toReturnDTO.setTotalCollected(enterpriseUser.getTotalCollected());
        toReturnDTO.setGoal(enterpriseUser.getGoal());
        toReturnDTO.setName(enterpriseUser.getName());
        toReturnDTO.setUserId(enterpriseUser.getUserId());
        toReturnDTO.setEnterpriseType(enterpriseUser.getEnterpriseType());
        List<String> areaNames = new ArrayList<>();
        for (Area area : enterpriseUser.getAreas()){
            areaNames.add(area.getName());
        }
        toReturnDTO.setAreas(areaNames);
        toReturnDTO.setDescription(enterpriseUser.getDescription());
        toReturnDTO.setGoal(enterpriseUser.getGoal());
        return toReturnDTO;
    }

    public ResponseEntity<Object> investInEnterprise(InvestDTO requestBody) {
        // Check session validation
        Optional<Session> optionalSession = sessionRepository.findSessionBySessionToken(requestBody.getSessionToken());
        if (optionalSession.isEmpty()) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }
        // Get investor and enterprise
        InvestorUser investor = (InvestorUser) optionalSession.get().getUser();
        EnterpriseUser enterprise = enterpriseUserRepository.findEnterpriseUserByUserId(requestBody.getEnterpriseId());

        if (enterprise.getEnterpriseType() == "Community") {
            enterprise.setTotalCollected(enterprise.getTotalCollected()+ requestBody.getAmount());
        }
        // If new investment, Create investmentRelationship and add values
        Optional<Investment> optionalInvestment = investmentRepository.findInvestmentByInvestorAndEnterprise(investor, enterprise);
        if (optionalInvestment.isEmpty()) {
            Investment investment = new Investment();
            investment.setEnterprise(enterprise);
            investment.setInvestor(investor);
            investment.setAmountInvested(requestBody.getAmount());
            investmentRepository.save(investment);
        } else {
            // If investment exists, update amount invested
            Investment investment = optionalInvestment.get();
            investment.setAmountInvested(investment.getAmountInvested() + requestBody.getAmount());
            investmentRepository.save(investment);
        }
        // Return success
        return new ResponseEntity<>("Investment made successfully", HttpStatus.OK);
    }
}
