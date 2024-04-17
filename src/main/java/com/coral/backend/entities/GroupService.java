/*
package com.coral.backend.services;


import com.coral.backend.entities.Group;
import com.coral.backend.entities.Users;
import com.coral.backend.repositories.GroupRepository;
import com.coral.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

*/
/*

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Object> createGroup(Map<String, Object> group){
        try {
            Group generatedGroup = new Group();
            generatedGroup.setTitle((String) group.get("title"));
            generatedGroup.setDescription((String) group.get("description"));
            generatedGroup.setPrivate((Boolean) group.get("isPrivate"));
            Optional<Users> optionalCreator = userRepository.findById((String) group.get("userId"));
            if(optionalCreator.isEmpty()){
                return new ResponseEntity<>("user/not-found", HttpStatus.NOT_FOUND);
            }
            else {
                Users creator = optionalCreator.get();
                generatedGroup.setCreatedBy(creator);
                Set<Users> userSet = generatedGroup.getUsers();
                userSet.add(creator);
                generatedGroup.setUsers(userSet);
                generatedGroup.setCreatedBy(creator);
                groupRepository.save(generatedGroup);

                creator.getGroups().add(generatedGroup);
                userRepository.save(creator);

                System.out.println("Group " + generatedGroup.getTitle() + " created successfully!");
                return new ResponseEntity<>("Group Generated", HttpStatus.OK);
            }

        }
        catch (DataIntegrityViolationException e){
            String errorMessage = e.getMostSpecificCause().getMessage();
            if(errorMessage.contains("title")){
                String response = "group/title-already-in-use";
                System.out.println(response);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }


    }

    public ResponseEntity<Object> getGroups(String id){
        Optional<Users> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            return new ResponseEntity<>("user/not-found", HttpStatus.NOT_FOUND);
        }
        else{
            Users user = optionalUser.get();
            List<Map<String, Object>> response = getGroupList(user);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    private static List<Map<String, Object>> getGroupList(Users user) {
        Set<Group> groups = user.getGroups();
        List<Map<String,Object>> response = new ArrayList<>();
        for(Group group : groups){
            Map<String,Object> groupMap = new HashMap<>();
            createGroup(group, groupMap);
            response.add(groupMap);
        }
        return response;
    }

    private static void createGroup(Group group, Map<String, Object> groupMap) {
        groupMap.put("id", group.getId());
        groupMap.put("title", group.getTitle());
        groupMap.put("description", group.getDescription());
        groupMap.put("isPrivate", group.isPrivate());
        groupMap.put("createdBy", group.getCreatedBy().getId());
    }


    public ResponseEntity<Object> getGroup(String id){
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if(optionalGroup.isEmpty()){
            return new ResponseEntity<>("group/not-found", HttpStatus.NOT_FOUND);
        }
        else{
            Group group = optionalGroup.get();
            Map<String,Object> response = new HashMap<>();
            createGroup(group, response);
            Set<Users> users = group.getUsers();
            response.put("users", Arrays.stream(users.toArray()).map(user -> ((Users) user).getId()).toArray());
            return new ResponseEntity<>(group, HttpStatus.OK);
        }
    }
}
*/