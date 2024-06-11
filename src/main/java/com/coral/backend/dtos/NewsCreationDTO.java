package com.coral.backend.dtos;

public class NewsCreationDTO {
    private String sessionToken;
    private String title;
    private String description;
    private String image;

    public String getSessionToken() {
        return sessionToken;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getImage() {
        return image;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
