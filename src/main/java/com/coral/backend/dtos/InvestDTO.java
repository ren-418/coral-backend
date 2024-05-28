package com.coral.backend.dtos;

public class InvestDTO {
  private String sessionToken;
  private long enterpriseId;
  private int amount;

  //Setters
  public void setAmount(int amount) {
    this.amount = amount;
  }
  public void setEnterpriseId(long enterpriseId) {
    this.enterpriseId = enterpriseId;
  }
  public void setSessionToken(String sessionToken) {
    this.sessionToken = sessionToken;
  }

  // Getters
  public String getSessionToken() {
    return sessionToken;
  }
  public int getAmount() {
    return amount;
  }
  public long getEnterpriseId() {
    return enterpriseId;
  }
}
