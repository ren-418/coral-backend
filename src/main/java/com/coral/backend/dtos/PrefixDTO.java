package com.coral.backend.dtos;

import java.util.List;

public class PrefixDTO {

    private String sessionToken;
    private String prefix;

    private List<String> prefixesResult;

    public String getPrefix() {
        return prefix;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public List<String> getPrefixesResult() {
        return prefixesResult;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public void setPrefixesResult(List<String> prefixesResult) {
        this.prefixesResult = prefixesResult;
    }
}

