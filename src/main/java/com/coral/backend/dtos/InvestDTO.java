package com.coral.backend.dtos;

public class InvestDTO {
  private String sessionToken;
  private long enterpriseId;
  private int amount;

  private boolean isPublic;

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
  public void setIsPublic(boolean isPublic) {this.isPublic = isPublic;}

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
  public boolean getIsPublic() {return isPublic;}
}
