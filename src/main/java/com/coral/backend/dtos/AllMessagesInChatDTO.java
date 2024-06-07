package com.coral.backend.dtos;

import com.coral.backend.entities.ChatMessage;

import java.util.List;

public class AllMessagesInChatDTO {
  private final String profileImage;
  private final String name;
  private final List<ChatMessage> messages;
  private final long clientId;

  public AllMessagesInChatDTO(String profileImage, String name, List<ChatMessage> lastMessage, long clientId) {
    this.profileImage = profileImage;
    this.name = name;
    this.messages = lastMessage;
    this.clientId = clientId;
  }

  public long getClientId() {
    return clientId;
  }

  public List<ChatMessage> getMessages() {
    return messages;
  }

  public String getName() {
    return name;
  }

  public String getProfileImage() {
    return profileImage;
  }
}
