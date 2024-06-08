package com.coral.backend.dtos;

import com.coral.backend.entities.ChatMessage;

public class ChatPreviewDTO {
  private final String profileImage;
  private final String name;
  private final ChatMessage lastMessage;
  private final long userId;

  public ChatPreviewDTO(String profileImage, String name, ChatMessage lastMessage, long userId) {
    this.profileImage = profileImage;
    this.name = name;
    this.lastMessage = lastMessage;
    this.userId = userId;
  }

  public long getUserId() {
    return userId;
  }

  public ChatMessage getLastMessage() {
    return lastMessage;
  }

  public String getName() {
    return name;
  }

  public String getProfileImage() {
    return profileImage;
  }
}
