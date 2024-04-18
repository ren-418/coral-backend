package com.coral.backend.services;

import com.coral.backend.dtos.*;
import com.coral.backend.entities.EnterpriseUser;
import com.coral.backend.entities.InvestorUser;
import com.coral.backend.entities.Session;
import com.coral.backend.entities.User;
import com.coral.backend.repositories.SessionRepository;
import com.coral.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;


    public ResponseEntity<Object> register(RegisterDTO user) {
       Optional<User> emailEntry = userRepository.findUserByEmail(user.getEmail());

        String response;

        if (emailEntry.isPresent()) {
            return new ResponseEntity<>("Email already in use", HttpStatus.CONFLICT);
        }
        switch (user.getAccountType()) {
            case "investor":
                InvestorUser investorUser = new InvestorUser();
                investorUser.setEmail(user.getEmail());
                investorUser.setPassword(user.getPassword());
                investorUser.setFirstLogin(true);
                userRepository.save(investorUser);
                response =  "Your account was created successfully";
                break;
            case "enterprise":
                EnterpriseUser enterpriseUser = new EnterpriseUser();
                enterpriseUser.setEmail(user.getEmail());
                enterpriseUser.setPassword(user.getPassword());
                enterpriseUser.setFirstLogin(true);
                userRepository.save(enterpriseUser);
                response =  "Your account was created successfully";
                break;
            default:
                return new ResponseEntity<>("That user type does not exist", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    public ResponseEntity<Object> login(LoginDTO userDTO) {
        Optional<User> optionalUser = userRepository.findUserByEmail(userDTO.getEmail());

        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>("That email is not registered", HttpStatus.NOT_FOUND);
        }

        Optional<Session> optionalSession = sessionRepository.findSessionByUser(optionalUser.get());

        User user = optionalUser.get();
        if (user.getPassword().equals(userDTO.getPassword())) {
            Session session = new Session();
            if (optionalSession.isEmpty()) {
                session.setUser(user);
                sessionRepository.save(session);
            }
            else{
                session = optionalSession.get();
            }

            if (user instanceof InvestorUser) {
                InvestorUser investor = (InvestorUser) user;
                return new ResponseEntity<>(session.getSessionToken(), HttpStatus.OK);
            } else if (user instanceof EnterpriseUser) {
                EnterpriseUser enterprise = (EnterpriseUser) user;
                return new ResponseEntity<>(session.getSessionToken(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Incorrect password", HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<Object> checkUser(CheckSessionDTO requestBody) {
        Optional<Session> optionalSession = sessionRepository.findSessionBySessionToken(requestBody.getSessionToken());

        if (optionalSession.isEmpty()) {
            return new ResponseEntity<>("Session expired", HttpStatus.NOT_FOUND);
        }

        User user = optionalSession.get().getUser();

        if (user instanceof InvestorUser) {
            InvestorUser investor = (InvestorUser) user;
            InvestorDTO investorDTO = new InvestorDTO();
            investorDTO.setEmail(investor.getEmail());
            investorDTO.setFirstLogin(investor.getFirstLogin());
            investorDTO.setUserId(investor.getUserId());
            investorDTO.setName(investor.getName());
            investorDTO.setDescription(investor.getDescription());
            investorDTO.setLocation(investor.getLocation());
            investorDTO.setAreas(investor.getAreas());
            investorDTO.setUserType(investor.getUserType());
            return new ResponseEntity<>(investorDTO, HttpStatus.OK);
        } else if (user instanceof EnterpriseUser) {
            EnterpriseUser enterprise = (EnterpriseUser) user;
            EnterpriseDTO enterpriseDTO = new EnterpriseDTO();
            enterpriseDTO.setEmail(enterprise.getEmail());
            enterpriseDTO.setFirstLogin(enterprise.getFirstLogin());
            enterpriseDTO.setUserId(enterprise.getUserId());
            enterpriseDTO.setName(enterprise.getName());
            enterpriseDTO.setDescription(enterprise.getDescription());
            enterpriseDTO.setLocation(enterprise.getLocation());
            enterpriseDTO.setAreas(enterprise.getAreas());
            enterpriseDTO.setUserType(enterprise.getUserType());
            return new ResponseEntity<>(enterpriseDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid user type", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> logout(LogoutDTO requestBody) {
        Optional<Session> optionalSession = sessionRepository.findSessionBySessionToken(requestBody.getSessionToken());

        if (optionalSession.isEmpty()) {
            return new ResponseEntity<>("Session expired", HttpStatus.NOT_FOUND);
        }

        sessionRepository.delete(optionalSession.get());
        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }

    public User checkAuth(String sessionToken){
        Optional<Session> optionalSession = sessionRepository.findSessionBySessionToken(sessionToken);

        if (optionalSession.isEmpty()) {
            return null;
        }

        return optionalSession.get().getUser();
    }
}