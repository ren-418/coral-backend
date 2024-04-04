package com.coral.backend.services;

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
            return new ResponseEntity<>("auth/email-already-in-use", HttpStatus.CONFLICT);
        }
        switch (user.getAccountType()) {
            case "investor":
                InvestorUser investorUser = new InvestorUser();
                investorUser.setEmail(user.getEmail());
                investorUser.setPassword(user.getPassword());
                userRepository.save(investorUser);
                response =  "User " + investorUser.getEmail() + " registered successfully!";
                break;
            case "enterprise":
                EnterpriseUser enterpriseUser = new EnterpriseUser();
                enterpriseUser.setEmail(user.getEmail());
                enterpriseUser.setPassword(user.getPassword());
                userRepository.save(enterpriseUser);
                response =  "User " + enterpriseUser.getEmail() + " registered successfully!";
                break;
            default:
                return new ResponseEntity<>("auth/invalid-account-type", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /*public ResponseEntity<Object> login(LoginDTO userDTO) {
        Optional<Users> optionalUser = convertToEntity(userDTO);

        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>("auth/user-not-found", HttpStatus.NOT_FOUND);
        }

        Users user = optionalUser.get();
        if (user.getPassword().equals(userDTO.getPassword())) {
            Optional<Session> optionalSession = sessionRepository.findSessionByUserId(user.getId());
            if (optionalSession.isPresent()) {
                Session session = optionalSession.get();
               if (sessionService.isSessionExpired(session.getId())) {
                   sessionService.addTime(session);
                   return new ResponseEntity<>(session.getId(), HttpStatus.OK);
               } else {
                   sessionRepository.delete(session);
               }
            }
            Session newSession = new Session();
            newSession.setUser(user);
            sessionRepository.save(newSession);
            return new ResponseEntity<>(newSession.getId(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("auth/wrong-password", HttpStatus.UNAUTHORIZED);
        }
    }

    public Optional<Users> convertToEntity(LoginDTO userDTO) {
        return userRepository.findUserByEmail(userDTO.getEmail());
    }*/
}