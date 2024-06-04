package com.coral.backend.entities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "messages")
public class ChatMessage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long messageId;
  private long senderId;
  @Column(name = "message")
  private String message;

  @Column(name = "time_stamp")
  private String timeStamp;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public long getSenderId() {
    return senderId;
  }

  public void setSenderId(long senderId) {
    this.senderId = senderId;
  }

  public long getMessageId() {
    return messageId;
  }

  public String getTimeStamp() {
    return timeStamp;
  }

  public void setMessageId(long messageId) {
    this.messageId = messageId;
  }

  public void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
  }
}
