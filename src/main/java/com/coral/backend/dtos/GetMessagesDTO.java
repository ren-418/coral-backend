package com.coral.backend.dtos;

public class GetMessagesDTO {
  private long receiverId;
  private String senderSessionToken;

  public long getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(long receiverId) {
    this.receiverId = receiverId;
  }

  public String getSenderSessionToken() {
    return senderSessionToken;
  }

  public void setSenderSessionToken(String sender) {
    this.senderSessionToken = sender;
  }
}
