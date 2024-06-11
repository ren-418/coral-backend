package com.coral.backend.repositories;

import com.coral.backend.entities.Follow;
import com.coral.backend.entities.InvestorUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    Follow findByFollowerAndFollowed(InvestorUser follower, InvestorUser followed);
    List<Follow> findAllByFollowed(InvestorUser followed);
}
