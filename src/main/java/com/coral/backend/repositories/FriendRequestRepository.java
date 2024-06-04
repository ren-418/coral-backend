/*package com.coral.backend.repositories;

import com.coral.backend.entities.FriendRequest;
import com.coral.backend.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface FriendRequestRepository extends JpaRepository<FriendRequest, String> {
    boolean existsByFromAndTo(Users from, Users to);

    Optional<FriendRequest> findAllByTo(Users user);
}
*/