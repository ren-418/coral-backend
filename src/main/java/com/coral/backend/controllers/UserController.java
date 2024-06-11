package com.coral.backend.controllers;

import com.coral.backend.dtos.*;
import com.coral.backend.services.UserService;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.mercadopago.MercadoPagoConfig;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    static {
        MercadoPagoConfig.setAccessToken("APP_USR-7604282588467181-061016-1691ac677ab01de231ee75606e68cc3b-1853222856");
    }

    @PostMapping("/create-preference")
    public ResponseEntity<Object> test(@RequestBody PreferenceDTO requestBody) throws MPException, MPApiException {
        try {
            PreferenceBackUrlsRequest backUrl = PreferenceBackUrlsRequest.builder()
                    .success("https://www.youtube.com/watch?v=-VD-l5BQsuE")
                    .pending("https://www.youtube.com/watch?v=-VD-l5BQsuE")
                    .failure("https://www.youtube.com/watch?v=-VD-l5BQsuE")
                    .build();
            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title(requestBody.getTitle())
                    .quantity(requestBody.getQuantity().intValue())
                    .unitPrice(new BigDecimal(requestBody.getPrice().toString()))
                    .id(requestBody.getSessionToken())
                    .currencyId("ARS")
                    .build();
            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(item);
            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items).backUrls(backUrl).autoReturn("approved").build();
            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);
            Map<String, String> response = new HashMap<>();
            response.put("id", preference.getId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (MPException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Autowired
    private UserService userService;

    /*@GetMapping("/{id}")
    public ResponseEntity<Users> getUser(@PathVariable String id) {
        Optional<Users> user = userRepository.findById(id);
        return user.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }*/
    @PostMapping("/create-investor-profile")
    public ResponseEntity<Object> createInvestorProfile(@RequestBody InvestorDTO requestBody){
        return userService.createInvestorProfile(requestBody);
    }

    @PostMapping("/create-enterprise-profile")
    public ResponseEntity<Object> createEnterpriseProfile(@RequestBody EnterpriseDTO requestBody){
        return userService.createEnterpriseProfile(requestBody);
    }

    @PostMapping("/enterprise")
    public ResponseEntity<Object> getEnterpriseProfile(@RequestBody EnterpriseDTO requestBody){
        return userService.getEnterpriseProfile(requestBody);
    }

    @PostMapping("/investor")
    public ResponseEntity<Object> getInvestorProfile(@RequestBody InvestorDTO requestBody){
        return userService.getInvestorProfile(requestBody);
    }

    @PostMapping("/invest")
    public ResponseEntity<Object> investInEnterprise(@RequestBody InvestDTO requestBody){
        return userService.investInEnterprise(requestBody);
    }

    @PostMapping("/follow")
    public ResponseEntity<Object> followInvestor(@RequestBody FollowInvestorDTO requestBody){
        return userService.followInvestor(requestBody);
    }

    @PostMapping("/unfollow")
    public ResponseEntity<Object> unfollowInvestor(@RequestBody FollowInvestorDTO requestBody){
        return userService.unfollowInvestor(requestBody);
    }

    @PostMapping("/is-following")
    public ResponseEntity<Object> isFollowing(@RequestBody FollowInvestorDTO requestBody){
        return userService.isFollowing(requestBody);
    }
}