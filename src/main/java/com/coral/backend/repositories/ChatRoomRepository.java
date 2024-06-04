package com.coral.backend.repositories;

import com.coral.backend.entities.ChatRoom;
import com.coral.backend.entities.EnterpriseUser;
import com.coral.backend.entities.Investment;
import com.coral.backend.entities.InvestorUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
  Optional<ChatRoom> findChatRoomByInvestorAndEnterprise(InvestorUser investor, EnterpriseUser enterprise);
}
