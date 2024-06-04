package com.coral.backend.repositories;

import com.coral.backend.entities.ChatMessage;
import com.coral.backend.entities.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {

}
