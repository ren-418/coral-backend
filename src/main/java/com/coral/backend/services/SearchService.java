package com.coral.backend.services;

import com.coral.backend.dtos.EnterpriseDTO;
import com.coral.backend.dtos.InvestorDTO;
import com.coral.backend.dtos.SearchDTO;
import com.coral.backend.entities.Area;
import com.coral.backend.entities.EnterpriseUser;
import com.coral.backend.entities.InvestorUser;
import com.coral.backend.entities.User;
import com.coral.backend.repositories.AreaRepository;
import com.coral.backend.repositories.EnterpriseUserRepository;
import com.coral.backend.repositories.InvestorUserRepository;
import com.coral.backend.repositories.UserRepository;
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
    private EnterpriseUserRepository enterpriseUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AreaRepository areaRepository;

    public ResponseEntity<Object> searchInvestors(SearchDTO searchDTO) {
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

        if (searchDTO.getAreas().isEmpty() && searchDTO.getLocations().isEmpty() && searchDTO.getInvestorType() >= 0){
            matchingInvestors.addAll(investorUserRepository.findAllByInvestorType(searchDTO.getInvestorType()));
        }
        else if(searchDTO.getAreas().isEmpty() && !searchDTO.getLocations().isEmpty() && searchDTO.getInvestorType() >= 0){
            for (String location : searchDTO.getLocations()){
                matchingInvestors.addAll(investorUserRepository.findAllByInvestorTypeAndLocation(searchDTO.getInvestorType(), location));
            }
        } else if (!searchDTO.getAreas().isEmpty() && searchDTO.getLocations().isEmpty() && searchDTO.getInvestorType() >= 0){
            for (Area area : areas){
                matchingInvestors.addAll(investorUserRepository.findAllByInvestorTypeAndAreas(searchDTO.getInvestorType(), area));
            }
        } else if(!searchDTO.getAreas().isEmpty() && !searchDTO.getLocations().isEmpty() && searchDTO.getInvestorType() >= 0){
            for (Area area : areas){
                for (String location : searchDTO.getLocations()){
                    matchingInvestors.addAll(investorUserRepository.findAllByInvestorTypeAndAreasAndLocation(searchDTO.getInvestorType(), area, location));
                }
            }
        } else if(searchDTO.getAreas().isEmpty() && !searchDTO.getLocations().isEmpty() && searchDTO.getInvestorType() < 0){
            for (String location : searchDTO.getLocations()){
                matchingInvestors.addAll(investorUserRepository.findAllByLocation(location));
            }
        } else if (!searchDTO.getAreas().isEmpty() && searchDTO.getLocations().isEmpty() && searchDTO.getInvestorType() < 0){
            for (Area area : areas){
                matchingInvestors.addAll(investorUserRepository.findAllByAreas(area));
            }
        } else if(!searchDTO.getAreas().isEmpty() && !searchDTO.getLocations().isEmpty() && searchDTO.getInvestorType() < 0){
            for (Area area : areas){
                for (String location : searchDTO.getLocations()){
                    matchingInvestors.addAll(investorUserRepository.findAllByAreasAndLocation(area, location));
                }
            }
        }

        if(!Objects.equals(searchDTO.getUserName(), "") && !matchingInvestors.isEmpty()){
            //Copy matchingInvestors to a new list to avoid ConcurrentModificationException
            List<User> matchingInvestorsCopy = new ArrayList<>(matchingInvestors);
            for (User user: matchingInvestorsCopy){
                boolean containsSubString = user.getName().toLowerCase().contains(searchDTO.getUserName().toLowerCase());
                if (!containsSubString){
                    matchingInvestors.remove(user);
                }
            }
        }
        else if(!Objects.equals(searchDTO.getUserName(), "") && matchingInvestors.isEmpty()){
            matchingInvestors.addAll(investorUserRepository.findAll());
            //Copy matchingInvestors to a new list to avoid ConcurrentModificationException
            List<User> matchingInvestorsCopy = new ArrayList<>(matchingInvestors);
            for (User user: matchingInvestorsCopy){
                boolean containsSubString = user.getName().toLowerCase().contains(searchDTO.getUserName().toLowerCase());
                if (!containsSubString){
                    matchingInvestors.remove(user);
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

    public ResponseEntity<Object> searchEnterprises(SearchDTO searchDTO) {
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

        if (searchDTO.getAreas().isEmpty() && searchDTO.getLocations().isEmpty() && !Objects.equals(searchDTO.getEnterpriseType(), "")){
            matchingInvestors.addAll(enterpriseUserRepository.findAllByEnterpriseType(searchDTO.getEnterpriseType()));
        }
        else if(searchDTO.getAreas().isEmpty() && !searchDTO.getLocations().isEmpty() && !Objects.equals(searchDTO.getEnterpriseType(), "")){
            for (String location : searchDTO.getLocations()){
                matchingInvestors.addAll(enterpriseUserRepository.findAllByEnterpriseTypeAndLocation(searchDTO.getEnterpriseType(), location));
            }
        } else if (!searchDTO.getAreas().isEmpty() && searchDTO.getLocations().isEmpty() && !Objects.equals(searchDTO.getEnterpriseType(), "")){
            for (Area area : areas){
                matchingInvestors.addAll(enterpriseUserRepository.findAllByEnterpriseTypeAndAreas(searchDTO.getEnterpriseType(), area));
            }
        } else if(!searchDTO.getAreas().isEmpty() && !searchDTO.getLocations().isEmpty() && !Objects.equals(searchDTO.getEnterpriseType(), "")){
            for (Area area : areas){
                for (String location : searchDTO.getLocations()){
                    matchingInvestors.addAll(enterpriseUserRepository.findAllByEnterpriseTypeAndAreasAndLocation(searchDTO.getEnterpriseType(), area, location));
                }
            }
        } else if(searchDTO.getAreas().isEmpty() && !searchDTO.getLocations().isEmpty() && Objects.equals(searchDTO.getEnterpriseType(), "")){
            for (String location : searchDTO.getLocations()){
                matchingInvestors.addAll(enterpriseUserRepository.findAllByLocation(location));
            }
        } else if (!searchDTO.getAreas().isEmpty() && searchDTO.getLocations().isEmpty() && Objects.equals(searchDTO.getEnterpriseType(), "")){
            for (Area area : areas){
                matchingInvestors.addAll(enterpriseUserRepository.findAllByAreas(area));
            }
        } else if(!searchDTO.getAreas().isEmpty() && !searchDTO.getLocations().isEmpty() && Objects.equals(searchDTO.getEnterpriseType(), "")){
            for (Area area : areas){
                for (String location : searchDTO.getLocations()){
                    matchingInvestors.addAll(enterpriseUserRepository.findAllByAreasAndLocation(area, location));
                }
            }
        }

        if(!Objects.equals(searchDTO.getUserName(), "") && !matchingInvestors.isEmpty()){
            //Copy matchingInvestors to a new list to avoid ConcurrentModificationException
            List<User> matchingInvestorsCopy = new ArrayList<>(matchingInvestors);
            for (User user: matchingInvestorsCopy){
                if(user.getName() != null){
                    boolean containsSubString = user.getName().toLowerCase().contains(searchDTO.getUserName().toLowerCase());
                    if (!containsSubString){
                        matchingInvestors.remove(user);
                    }
                }
            }
        }
        else if(!Objects.equals(searchDTO.getUserName(), "") && matchingInvestors.isEmpty()){
            matchingInvestors.addAll(investorUserRepository.findAll());
            //Copy matchingInvestors to a new list to avoid ConcurrentModificationException
            List<User> matchingInvestorsCopy = new ArrayList<>(matchingInvestors);
            for (User user: matchingInvestorsCopy){
                boolean containsSubString = user.getName().toLowerCase().contains(searchDTO.getUserName().toLowerCase());
                if (!containsSubString){
                    matchingInvestors.remove(user);
                }
            }
        }

        List<EnterpriseDTO> FrontDataPackage = new ArrayList<>();
        if (matchingInvestors.isEmpty()){
            return new ResponseEntity<>("No matching enterprises found", HttpStatus.NOT_FOUND);
        }
        for (User user: matchingInvestors){
            if(!user.getFirstLogin()) {
                EnterpriseUser enterprise = (EnterpriseUser) user;
                EnterpriseDTO dataPackage = new EnterpriseDTO();

                dataPackage.setName(enterprise.getName());
                dataPackage.setEnterpriseType(enterprise.getEnterpriseType());
                dataPackage.setLocation(enterprise.getLocation());
                dataPackage.setDescription(enterprise.getDescription());
                List<String> areasString = new ArrayList<>();
                for (Area area : enterprise.getAreas()) {
                    areasString.add(area.getName());
                }
                dataPackage.setAreas(areasString);
                dataPackage.setProfileImage(decodeImage(enterprise.getProfileImage()));
                dataPackage.setGoal(enterprise.getGoal());
                dataPackage.setMinimumInvestment(enterprise.getMinimumInvestment());
                dataPackage.setTotalProfitReturn(enterprise.getTotalProfitReturn());

                FrontDataPackage.add(dataPackage);
            }
        }
        return new ResponseEntity<>(FrontDataPackage, HttpStatus.OK);
    }
}
