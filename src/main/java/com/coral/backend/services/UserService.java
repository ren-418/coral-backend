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
            Optional<Area> newArea = areaRepository.findAreaByName(area);
            if(newArea.isPresent()){
                areaList.add(newArea.get());
            }
        }
        user.setAreas(areaList);
        user.setInitialDate(getActualDate());
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
            Optional<Area> newArea = areaRepository.findAreaByName(area);
            if(newArea.isPresent()){
                areaList.add(newArea.get());
            }
        }
        user.setAreas(areaList);
        user.setInitialDate(getActualDate());
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

    public LocalDate getActualDate(){
        return LocalDate.now();
    }

    public ResponseEntity<Object> getEnterpriseProfile(EnterpriseDTO requestBody) {
        Optional<Session> optionalSession = sessionRepository.findSessionBySessionToken(requestBody.getSessionToken());

        if (optionalSession.isEmpty()) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }

        EnterpriseUser enterpriseUser = enterpriseUserRepository.findEnterpriseUserByUserId(requestBody.getUserId());

        EnterpriseDTO toReturnDTO = enterpriseUser.toDTO();
        List<InvestorDTO> investors = new ArrayList<>();
        Optional<List<Investment>> investmentsOptional = investmentRepository.findAllByEnterprise(enterpriseUser);

        if(investmentsOptional.isPresent()){
            List<Investment> investments = investmentsOptional.get();
            for(Investment investment: investments){
                investors.add(investment.getInvestor().toDTO());
            }
        }

        toReturnDTO.setInvestors(investors);

        return new ResponseEntity<>(toReturnDTO, HttpStatus.OK);
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

        if (Objects.equals(enterprise.getEnterpriseType(), "Community")) {
            enterprise.setTotalCollected(enterprise.getTotalCollected()+ requestBody.getAmount());
            enterpriseUserRepository.save(enterprise);
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
