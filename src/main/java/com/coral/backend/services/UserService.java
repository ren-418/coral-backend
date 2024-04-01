/*package com.coral.backend.services;

import com.coral.backend.entities.FriendRequest;
import com.coral.backend.entities.Users;
import com.coral.backend.repositories.FriendRequestRepository;
import com.coral.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
*/
/*
@Service

public class UserService {

    @Autowired
    private FriendRequestRepository friendRequestRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Object> sendFriendRequest(Map<String, String> friendRequestBody) {
        Optional<Users> fromEntry = userRepository.findById(friendRequestBody.get("from_id"));
        Optional<Users> toEntry = userRepository.findById(friendRequestBody.get("to_id"));

        if (fromEntry.isEmpty() || toEntry.isEmpty()) {
            return ResponseEntity.badRequest().body("user/not-found");
        }

        if (fromEntry.get().getFriends().contains(toEntry.get())) {
            return ResponseEntity.badRequest().body("user/already-a-friend");
        }

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setFrom(fromEntry.get());
        friendRequest.setTo(toEntry.get());

        if(friendRequestRepository.existsByFromAndTo(fromEntry.get(), toEntry.get())){
            return ResponseEntity.badRequest().body("friend-request/already-sent");
        }
        friendRequestRepository.save(friendRequest);

        return ResponseEntity.ok("friend-request/sent");

    }

    public ResponseEntity<Object> getIdByUsername(Map<String, String> username) {
        Optional<Users> user = userRepository.findUserByUsername(username.get("username"));
        return user.<ResponseEntity<Object>>map(value -> ResponseEntity.ok(value.getId())).orElseGet(() -> ResponseEntity.badRequest().body("user/not-found"));
    }

    public ResponseEntity<Object> getAllRequests(String id) {
        Optional<Users> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("user/not-found");
        }
        List<Map<String,Object>> response = new ArrayList<>();
        for (FriendRequest friendRequest : user.get().getReceivedRequests()) {
            response.add(Map.of(
                    "id", friendRequest.getId(),
                    "from", friendRequest.getFrom().getUsername(),
                    "to", friendRequest.getTo().getUsername(),
                    "created_at", friendRequest.getCreatedAt()
            ));
        }
        return ResponseEntity.ok(response);
    }
}
*/