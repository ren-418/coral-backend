package com.coral.backend.dtos;

import java.util.List;

public class AllChatsPreviewDTO {
  private final List<ChatPreviewDTO> chatPreviews;

  public AllChatsPreviewDTO(List<ChatPreviewDTO> chatPreviews) {
    this.chatPreviews = chatPreviews;
  }

  public List<ChatPreviewDTO> getChatPreviews() {
    return chatPreviews;
  }
}
