package com.coral.backend.dtos;

public class PreferenceDTO {

    private String sessionToken;

    private String title;

    private Number price;

    private Number quantity;

    public String getSessionToken() {
        return sessionToken;
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
