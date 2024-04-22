package com.coral.backend.services;

import com.coral.backend.dtos.InvestorDTO;
import com.coral.backend.dtos.SearchDTO;
import com.coral.backend.entities.Area;
import com.coral.backend.entities.InvestorUser;
import com.coral.backend.entities.User;
import com.coral.backend.repositories.AreaRepository;
import com.coral.backend.repositories.InvestorUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.coral.backend.services.UserService.decodeImage;

@Service
public class SearchService {

    @Autowired
    private InvestorUserRepository investorUserRepository;

    @Autowired
    private AreaRepository areaRepository;

    public ResponseEntity<Object> searchAsEnterprise(SearchDTO searchDTO) {
        List<Area> areas = new ArrayList<>();
        Set<User> matchingInvestors = new HashSet<>();

        //Transform List<String> to List<Area>
        if (searchDTO.getAreas() != null){
            for (String area : searchDTO.getAreas()) {
                Optional<Area> areaOptional = areaRepository.findAreaByName(area);
                if (areaOptional.isEmpty()) {
                    return new ResponseEntity<>("Area not found", HttpStatus.NOT_FOUND);
                }
                areas.add(areaOptional.get());
            }
        }

        if (searchDTO.getAreas().isEmpty() && searchDTO.getLocations().isEmpty()){
            matchingInvestors.addAll(investorUserRepository.findAllByInvestorType(searchDTO.getInvestorType()));
        }
        else if(searchDTO.getAreas().isEmpty() && !searchDTO.getLocations().isEmpty()){
            for (String location : searchDTO.getLocations()){
                matchingInvestors.addAll(investorUserRepository.findAllByInvestorTypeAndLocation(searchDTO.getInvestorType(), location));
            }
        } else if (!searchDTO.getAreas().isEmpty() && searchDTO.getLocations().isEmpty()){
            for (Area area : areas){
                matchingInvestors.addAll(investorUserRepository.findAllByInvestorTypeAndAreas(searchDTO.getInvestorType(), area));
            }
        } else if(!searchDTO.getAreas().isEmpty() && !searchDTO.getLocations().isEmpty()){
            for (Area area : areas){
                for (String location : searchDTO.getLocations()){
                    matchingInvestors.addAll(investorUserRepository.findAllByInvestorTypeAndAreasAndLocation(searchDTO.getInvestorType(), area, location));
                }
            }
        }

        List<InvestorDTO> FrontDataPackage = new ArrayList<>();
        if (matchingInvestors.isEmpty()){
            return new ResponseEntity<>("No matching investors found", HttpStatus.NOT_FOUND);
        }
        for (User user: matchingInvestors){
            if(!user.getFirstLogin()) {
                InvestorUser investor = (InvestorUser) user;
                InvestorDTO dataPackage = new InvestorDTO();

                dataPackage.setName(investor.getName());
                dataPackage.setInvestorType(investor.getInvestorType());
                dataPackage.setLocation(investor.getLocation());
                dataPackage.setDescription(investor.getDescription());
                List<String> areasString = new ArrayList<>();
                for (Area area : investor.getAreas()) {
                    areasString.add(area.getName());
                }
                dataPackage.setAreas(areasString);
                dataPackage.setProfilePicture(decodeImage(investor.getProfileImage()));

                FrontDataPackage.add(dataPackage);
            }
        }
        return new ResponseEntity<>(FrontDataPackage, HttpStatus.OK);
    }

}
