package com.coral.backend.dtos;

public class PreferenceDTO {

    private String sessionToken;

    private long enterpriseId;

    private String title;

    private Number price;

    private Number quantity;

    private Boolean isPublic;

    public String getSessionToken() {
        return sessionToken;
    }

    public long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getTitle() {
        return title;
    }

    public Number getPrice() {
        return price;
    }

    public Number getQuantity() {
        return quantity;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(Number price) {
        this.price= price;
    }

    public void setQuantity(Number quantity) {
        this.quantity = quantity;
    }
}
