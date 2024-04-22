package com.coral.backend.services;

import com.coral.backend.dtos.InvestorDTO;
import com.coral.backend.dtos.SearchDTO;
import com.coral.backend.entities.Area;
import com.coral.backend.entities.InvestorUser;
import com.coral.backend.repositories.AreaRepository;
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

    @Autowired
    private AreaRepository areaRepository;

    public ResponseEntity<Object> searchAsEnterprise(SearchDTO searchDTO) {
        List<Area> areas = new ArrayList<>();
        Optional<List<InvestorUser>> matchingInvestors;

        if (searchDTO.getAreas() != null){
            for (String area : searchDTO.getAreas()) {
                Optional<Area> areaOptional = areaRepository.findAreaByName(area);
                if (areaOptional.isEmpty()) {
                    return new ResponseEntity<>("Area not found", HttpStatus.NOT_FOUND);
                }
                areas.add(areaOptional.get());
            }
            matchingInvestors = userRepository.findMatchingInvestors(searchDTO.getInvestorType(),searchDTO.getLocations(), areas);
        }else{
            matchingInvestors = userRepository.findMatchingInvestors(searchDTO.getInvestorType(),searchDTO.getLocations(), null);
        }
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
            List<String> areasString = new ArrayList<>();
            for(Area area : investor.getAreas()){
                areasString.add(area.getName());
            }
            dataPackage.setAreas(areasString);
            FrontDataPackage.add(dataPackage);
        }
        return new ResponseEntity<>(FrontDataPackage, HttpStatus.OK);
    }

}
