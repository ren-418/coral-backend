package com.coral.backend.services;

import com.coral.backend.dtos.InvestorDTO;
import com.coral.backend.dtos.SearchDTO;
import com.coral.backend.entities.InvestorUser;
import com.coral.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SearchService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Object> searchAsEnterprise(SearchDTO searchDTO) {
        Optional<List<InvestorUser>> matchingInvestors = userRepository.findMatchingInvestors(searchDTO.getInvestorType(),searchDTO.getLocation(),searchDTO.getInterests());
        List<InvestorDTO> FrontDataPackage = new ArrayList<>();
        if (matchingInvestors.isEmpty()){
            return new ResponseEntity<>("No matching investors found", HttpStatus.NOT_FOUND);
        }
        for (InvestorUser investor: matchingInvestors.get()){
            InvestorDTO dataPackage = new InvestorDTO();
            dataPackage.setName(investor.getName());
            dataPackage.setInvestorType(investor.getInvestorType());
            dataPackage.setLocation(investor.getLocation());
            dataPackage.setDescription(investor.getDescription());
            dataPackage.setAreas(investor.getAreas());
            FrontDataPackage.add(dataPackage);
        }
        return new ResponseEntity<>(FrontDataPackage, HttpStatus.OK);
    }

}
