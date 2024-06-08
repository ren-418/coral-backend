package com.coral.backend.dtos;

import com.coral.backend.entities.ChatMessage;

import java.util.List;

public class ChatRoomDTO {
  private List<ChatMessage> messages;
  private EnterpriseDTO enterpriseDTO;
  private InvestorDTO investorDTO;

  public void setMessages(List<ChatMessage> messages) {
    this.messages = messages;
  }

  public void setEnterpriseDTO(EnterpriseDTO enterpriseDTO) {
    this.enterpriseDTO = enterpriseDTO;
  }

  public void setInvestorDTO(InvestorDTO investorDTO) {
    this.investorDTO = investorDTO;
  }

  public List<ChatMessage> getMessages() {
    return messages;
  }

  public EnterpriseDTO getEnterpriseDTO() {
    return enterpriseDTO;
  }

  public InvestorDTO getInvestorDTO() {
    return investorDTO;
  }
}
