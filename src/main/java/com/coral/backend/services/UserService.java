package com.coral.backend.services;

import com.coral.backend.dtos.*;
import com.coral.backend.entities.*;
import com.coral.backend.entities.DatedInvestment;
import com.coral.backend.repositories.*;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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

    @Autowired
    private InvestorUserRepository investorUserRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private DatedInvestmentRepository datedInvestmentRepository;

  
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

        DatedInvestment datedInvestment = new DatedInvestment();
        datedInvestment.setEnterprise(enterprise);
        datedInvestment.setInvestor(investor);
        datedInvestment.setAmountInvested(requestBody.getAmount());
        datedInvestment.setTimeStamp(getActualDate().toString());
        datedInvestmentRepository.save(datedInvestment);
        
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
        String receiverEmail=enterprise.getEmail();
        String subject="Investment Notification";
        String text="You have received an investment of $USD "+requestBody.getAmount()+" from "+investor.getName();
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(text);
        email.setTo(receiverEmail);
        email.setFrom("coral.recoveryteam@gmail.com");
        mailSender.send(email);

        Notification notification = new Notification();
        notification.setFrom(investor);
        notification.setTo(enterprise);
        notification.setMessage(text);
        notification.setRead(false);
        notification.setTimeStamp(getActualDate().toString());
        notificationRepository.save(notification);

        if (requestBody.getIsPublic()) {
            text=investor.getName()+" has invested $USD "+requestBody.getAmount()+" in "+enterprise.getName()+".";

            List<Follow> followers = followRepository.findAllByFollowed(investor);
            for (Follow follow : followers) {
                Notification notificationFollow = new Notification();
                notificationFollow.setFrom(investor);
                notificationFollow.setTo(follow.getFollower());
                notificationFollow.setMessage(text);
                notificationFollow.setRead(false);
                notificationFollow.setTimeStamp(getActualDate().toString());
                notificationRepository.save(notificationFollow);
            }
        }

        if (enterprise.getTotalCollected()>=enterprise.getGoal()){
            text="Congratulations! You have reached your goal of $USD "+enterprise.getGoal()+" in "+enterprise.getName()+".";
            Notification goalNotification = new Notification();
            goalNotification.setFrom(enterprise);
            goalNotification.setTo(enterprise);
            goalNotification.setMessage(text);
            goalNotification.setRead(false);
            goalNotification.setTimeStamp(getActualDate().toString());
            notificationRepository.save(goalNotification);
            text=enterprise.getName()+" has reached its goal of $USD "+enterprise.getGoal()+".";

            Optional<List<Investment>> investmentsOptional = investmentRepository.findAllByEnterprise(enterprise);

            if(investmentsOptional.isPresent()){
                List<Investment> investments = investmentsOptional.get();
                for(Investment investment: investments){
                    Notification achievedGoal = new Notification();
                    achievedGoal.setFrom(enterprise);
                    achievedGoal.setTo(investment.getInvestor());
                    achievedGoal.setMessage(text);
                    achievedGoal.setRead(false);
                    achievedGoal.setTimeStamp(getActualDate().toString());
                    notificationRepository.save(achievedGoal);
                }
            }
        }

        // Return success
        return new ResponseEntity<>("Investment made successfully", HttpStatus.OK);
    }

    public ResponseEntity<Object> getInvestorProfile(InvestorDTO requestBody) {
        Optional<Session> optionalSession = sessionRepository.findSessionBySessionToken(requestBody.getSessionToken());

        if (optionalSession.isEmpty()) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }

        Optional<User> user = userRepository.findUserByUserId(requestBody.getUserId());

        if(user.isEmpty()){
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        InvestorUser investorUser = (InvestorUser) user.get();

        InvestorDTO toReturnDTO = investorUser.toDTO();
        List<EnterpriseDTO> enterprises = new ArrayList<>();
        Optional<List<Investment>> investmentsOptional = investmentRepository.findAllByInvestor(investorUser);

        if(investmentsOptional.isPresent()){
            List<Investment> investments = investmentsOptional.get();
            for(Investment investment: investments){
                enterprises.add(investment.getEnterprise().toDTO());
            }
        }

        toReturnDTO.setEnterprises(enterprises);

        return new ResponseEntity<>(toReturnDTO, HttpStatus.OK);
    }

    public ResponseEntity<Object> followInvestor(FollowInvestorDTO requestBody) {
        Optional<Session> optionalSession = sessionRepository.findSessionBySessionToken(requestBody.getSessionToken());
        if (optionalSession.isEmpty()) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }

        User user = optionalSession.get().getUser();
        InvestorUser follower = investorUserRepository.findInvestorUserByUserId(user.getUserId());
        InvestorUser followed = investorUserRepository.findInvestorUserByUserId(requestBody.getInvestorId());


        if (followed == null) {
            return new ResponseEntity<>("Investor not found", HttpStatus.BAD_REQUEST);
        }
        if (followRepository.findByFollowerAndFollowed(follower, followed) != null) {
            return new ResponseEntity<>("Investor already followed", HttpStatus.CONFLICT);
        }

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowed(followed);
        followRepository.save(follow);

        Notification notification = new Notification();
        notification.setFrom(follower);
        notification.setTo(followed);
        notification.setMessage(follower.getName()+" has started following you.");
        notification.setRead(false);
        notification.setTimeStamp(getActualDate().toString());
        notificationRepository.save(notification);

        return new ResponseEntity<>("You are now following " + followed.getName(), HttpStatus.OK);
    }

    public ResponseEntity<Object> unfollowInvestor(FollowInvestorDTO requestBody) {
        Optional<Session> optionalSession = sessionRepository.findSessionBySessionToken(requestBody.getSessionToken());
        if (optionalSession.isEmpty()) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }
        User user = optionalSession.get().getUser();
        InvestorUser follower = investorUserRepository.findInvestorUserByUserId(user.getUserId());
        InvestorUser followed = investorUserRepository.findInvestorUserByUserId(requestBody.getInvestorId());

        if (followed == null) {
            return new ResponseEntity<>("Investor not found", HttpStatus.BAD_REQUEST);
        }

        if (followRepository.findByFollowerAndFollowed(follower, followed) == null) {
            return new ResponseEntity<>("Did not follow user", HttpStatus.CONFLICT);
        }

        Follow follow = followRepository.findByFollowerAndFollowed(follower, followed);
        followRepository.delete(follow);

        return new ResponseEntity<>("You no longer follow" + followed.getName(), HttpStatus.OK);
    }

    public ResponseEntity<Object> isFollowing(FollowInvestorDTO requestBody) {
        Optional<Session> optionalSession = sessionRepository.findSessionBySessionToken(requestBody.getSessionToken());
        if (optionalSession.isEmpty()) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }
        User user = optionalSession.get().getUser();
        InvestorUser follower = investorUserRepository.findInvestorUserByUserId(user.getUserId());
        InvestorUser followed = investorUserRepository.findInvestorUserByUserId(requestBody.getInvestorId());

        if (followRepository.findByFollowerAndFollowed(follower, followed) == null) {
            return new ResponseEntity<>("Not following", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Already follows", HttpStatus.OK);
    }

    public ResponseEntity<Object> getAllActivity(ActivityDTO requestBody) {
        Optional<Session> optionalSession = sessionRepository.findSessionBySessionToken(requestBody.getSessionToken());
        if (optionalSession.isEmpty()) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }
        if (Objects.equals(requestBody.getTo(), "")){
            requestBody.setTo(getActualDate().toString());
        }
        User user = optionalSession.get().getUser();
        if (user instanceof InvestorUser){
            InvestorUser investor = investorUserRepository.findInvestorUserByUserId(user.getUserId());
            Optional<List<DatedInvestment>> allInvestmentsInRange=datedInvestmentRepository.findAllByInvestorAndTimeStampBetween(investor,requestBody.getFrom(),requestBody.getTo());
            if (allInvestmentsInRange.isPresent()){
                Map<String,Integer> activities=new HashMap<>();
                for (DatedInvestment investment:allInvestmentsInRange.get()){
                    String enterpriseName=investment.getEnterprise().getName();
                    if (activities.containsKey(enterpriseName)){
                        activities.put(enterpriseName,activities.get(enterpriseName)+investment.getAmountInvested());
                    }else{
                        activities.put(enterpriseName,investment.getAmountInvested());
                    }
                }
                List<InvestmentInfoDTO> investmentInfoDTOS=new ArrayList<>();
                for (Map.Entry<String,Integer> entry:activities.entrySet()){
                    InvestmentInfoDTO investmentInfoDTO=new InvestmentInfoDTO();
                    investmentInfoDTO.setName(entry.getKey());
                    investmentInfoDTO.setAmount(entry.getValue());
                    investmentInfoDTOS.add(investmentInfoDTO);
                }
                PdfDTO pdfDTO=new PdfDTO();
                pdfDTO.setInvestmentInfo(investmentInfoDTOS);
                return new ResponseEntity<>(pdfDTO,HttpStatus.OK);
            }
        } else if (user instanceof EnterpriseUser) {
            EnterpriseUser enterprise = enterpriseUserRepository.findEnterpriseUserByUserId(user.getUserId());
            Optional<List<DatedInvestment>> allInvestmentsInRange=datedInvestmentRepository.findAllByEnterpriseAndTimeStampBetween(enterprise,requestBody.getFrom(),requestBody.getTo());
            if (allInvestmentsInRange.isPresent()){
                Map<String,Integer> activities=new HashMap<>();
                for (DatedInvestment investment:allInvestmentsInRange.get()){
                    String investorName=investment.getInvestor().getName();
                    if (activities.containsKey(investorName)){
                        activities.put(investorName,activities.get(investorName)+investment.getAmountInvested());
                    }else{
                        activities.put(investorName,investment.getAmountInvested());
                    }
                }
                List<InvestmentInfoDTO> investmentInfoDTOS=new ArrayList<>();
                for (Map.Entry<String,Integer> entry:activities.entrySet()){
                    InvestmentInfoDTO investmentInfoDTO=new InvestmentInfoDTO();
                    investmentInfoDTO.setName(entry.getKey());
                    investmentInfoDTO.setAmount(entry.getValue());
                    investmentInfoDTOS.add(investmentInfoDTO);
                }
                PdfDTO pdfDTO=new PdfDTO();
                pdfDTO.setInvestmentInfo(investmentInfoDTOS);
                return new ResponseEntity<>(pdfDTO,HttpStatus.OK);
            }
        }
        else{
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("No activities found", HttpStatus.NOT_FOUND);
    }
}
