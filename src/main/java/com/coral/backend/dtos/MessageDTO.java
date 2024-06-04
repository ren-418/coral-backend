package com.coral.backend.dtos;

import com.coral.backend.entities.User;

public class MessageDTO {
  private String message;
  private long receiverId;
  private String senderSessionToken;

  public String getMessage() {
    return message;
  }

  public long getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(long receiverId) {
    this.receiverId = receiverId;
  }

  public String getSenderSessionToken() {
    return senderSessionToken;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setSenderSessionToken(String sender) {
    this.senderSessionToken = sender;
  }
}
