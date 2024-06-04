package com.coral.backend.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "chat_rooms")
public class ChatRoom {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long chatRoomId;
  @ManyToOne
  @JoinColumn(name = "investor_id")
  private InvestorUser investor;
  @ManyToOne
  @JoinColumn(name = "enterprise_id")
  private EnterpriseUser enterprise;
  @OneToMany
  private List<ChatMessage> messages;

  public InvestorUser getInvestor() {
    return investor;
  }

  public EnterpriseUser getEnterprise() {
    return enterprise;
  }

  public void setInvestor(InvestorUser investor) {
    this.investor = investor;
  }

  public void setEnterprise(EnterpriseUser enterprise) {
    this.enterprise = enterprise;
  }

  public long getChatRoomId() {
    return chatRoomId;
  }

  public void setChatRoomId(long chatRoomId) {
    this.chatRoomId = chatRoomId;
  }

  public List<ChatMessage> getMessages() {
    return messages;
  }

  public void setMessages(List<ChatMessage> messages) {
    this.messages = messages;
  }
}
