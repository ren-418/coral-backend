package com.coral.backend.services;

import com.coral.backend.dtos.CheckSessionDTO;
import com.coral.backend.dtos.LoginDTO;
import com.coral.backend.dtos.RegisterDTO;
import com.coral.backend.entities.EnterpriseUser;
import com.coral.backend.entities.InvestorUser;
import com.coral.backend.entities.User;
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
                userRepository.save(investorUser);
                response =  "Your account was created successfully";
                break;
            case "enterprise":
                EnterpriseUser enterpriseUser = new EnterpriseUser();
                enterpriseUser.setEmail(user.getEmail());
                enterpriseUser.setPassword(user.getPassword());
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

        User user = optionalUser.get();
        if (user.getPassword().equals(userDTO.getPassword())) {
            if (user instanceof InvestorUser) {
                InvestorUser investor = (InvestorUser) user;
                return new ResponseEntity<>(investor, HttpStatus.OK);
            } else if (user instanceof EnterpriseUser) {
                EnterpriseUser enterprise = (EnterpriseUser) user;
                return new ResponseEntity<>(enterprise, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Incorrect password", HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<Object> checkUser(CheckSessionDTO requestBody) {
        Optional<User> optionalUser = userRepository.findUserByUserId(requestBody.getUserId());

        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();

        if (user instanceof InvestorUser) {
            InvestorUser investor = (InvestorUser) user;
            return new ResponseEntity<>(investor, HttpStatus.OK);
        } else if (user instanceof EnterpriseUser) {
            EnterpriseUser enterprise = (EnterpriseUser) user;
            return new ResponseEntity<>(enterprise, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}