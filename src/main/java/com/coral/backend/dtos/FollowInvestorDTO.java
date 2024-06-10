package com.coral.backend.dtos;

public class FollowInvestorDTO {
    private String sessionToken;
    private long investorId;

    public String getSessionToken() {
        return sessionToken;
    }
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
    public long getInvestorId() {
        return investorId;
    }
    public void setInvestorId(long investorId) {
        this.investorId = investorId;
    }
}
