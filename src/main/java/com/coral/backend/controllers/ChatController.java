package com.coral.backend.controllers;

import com.coral.backend.dtos.CheckSessionDTO;
import com.coral.backend.dtos.GetMessagesDTO;
import com.coral.backend.dtos.MessageDTO;
import com.coral.backend.services.ChatService;
import com.coral.backend.services.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {
  @Autowired
  ChatService chatService;

  @PostMapping("/send-message")
  public ResponseEntity<Object> sendMessage(@RequestBody MessageDTO messageDTO) {
    return chatService.sendMessage(messageDTO);
  }

  @PostMapping("/get-messages")
  public ResponseEntity<Object> getRecommendedEnterprises(@RequestBody GetMessagesDTO getMessageDTO) {
    return chatService.getMessages(getMessageDTO);
  }

  @PostMapping("/get-chats")
  public ResponseEntity<Object> getChats(@RequestBody CheckSessionDTO checkSessionDTO) {
    return chatService.getChats(checkSessionDTO);
  }
}
