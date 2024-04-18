package com.coral.backend.services;

import com.coral.backend.dtos.*;
import com.coral.backend.entities.*;
import com.coral.backend.repositories.ResetTokenRepository;
import com.coral.backend.repositories.SessionRepository;
import com.coral.backend.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ResetTokenRepository passwordTokenRepository;

    @Autowired
    private JavaMailSenderImpl mailSender;

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

    public ResponseEntity<Object> resetPassword(ForgotPasswordDTO forgotPasswordDTO){
        Optional<User> user = userRepository.findUserByEmail(forgotPasswordDTO.getEmail());
        if (user.isEmpty()) {
            return new ResponseEntity<>("There is no user with the given email", HttpStatus.NOT_FOUND);
        }
        Optional<ResetToken> resetToken=passwordTokenRepository.findResetTokenByUser(user.get());
        if (resetToken.isPresent()) {
            passwordTokenRepository.delete(resetToken.get());
        }
        String token = UUID.randomUUID().toString();
        ResetToken myToken = new ResetToken();
        myToken.setToken(token);
        myToken.setUser(user.get());
        passwordTokenRepository.save(myToken);
        mailSender.send(constructResetTokenEmail(token, user.get()));
        return new ResponseEntity<>("Password reset email sent", HttpStatus.OK);
    }

    public ResponseEntity<Object> checkIfTokenCoincidesWithUser(ForgotPasswordDTO forgotPasswordDTO){
        Optional<User> user = userRepository.findUserByEmail(forgotPasswordDTO.getEmail());
        if (user.isEmpty()) {
            return new ResponseEntity<>("There is no user with the given email", HttpStatus.NOT_FOUND);
        }
        Optional<ResetToken> resetToken=passwordTokenRepository.findResetTokenByUser(user.get());
        if (resetToken.isEmpty()) {
            return new ResponseEntity<>("There is no reset token for the given user", HttpStatus.NOT_FOUND);
        }
        if (resetToken.get().getToken().equals(forgotPasswordDTO.getToken())) {
            return new ResponseEntity<>("Token matches user", HttpStatus.OK);
        }
        return new ResponseEntity<>("Token does not match user", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> changePassword(ForgotPasswordDTO forgotPasswordDTO){
        Optional<User> user = userRepository.findUserByEmail(forgotPasswordDTO.getEmail());
        if (user.isEmpty()) {
            return new ResponseEntity<>("There is no user with the given email", HttpStatus.NOT_FOUND);
        }
        Optional<ResetToken> resetToken=passwordTokenRepository.findResetTokenByUser(user.get());
        if (resetToken.isEmpty()) {
            return new ResponseEntity<>("There is no reset token for the given user", HttpStatus.NOT_FOUND);
        }
        if (resetToken.get().getToken().equals(forgotPasswordDTO.getToken())) {
            user.get().setPassword(forgotPasswordDTO.getPassword());
            userRepository.save(user.get());
            passwordTokenRepository.delete(resetToken.get());
            return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Token does not match user", HttpStatus.NOT_FOUND);
    }

    private SimpleMailMessage constructResetTokenEmail(String token, User user) {
        String message = "This is your reset password code:";
        return constructEmail("Reset Password", message + " \r\n" + token, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body, User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom("coral.recoveryteam@gmail.com");
        return email;
    }
}