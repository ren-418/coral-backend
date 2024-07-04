package com.coral.backend.dtos;

import java.util.List;

public class PrefixDTO {

    private String sessionToken;
    private String prefix;

    private List<MentionDTO> prefixesResult;

    public String getPrefix() {
        return prefix;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public List<MentionDTO> getPrefixesResult() {
        return prefixesResult;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public void setPrefixesResult(List<MentionDTO> prefixesResult) {
        this.prefixesResult = prefixesResult;
    }
}

