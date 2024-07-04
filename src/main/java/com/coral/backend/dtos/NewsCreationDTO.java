package com.coral.backend.dtos;

import java.util.List;

public class NewsCreationDTO {
    private String sessionToken;
    private String title;
    private String description;
    private String image;
    private List<String> tags;

    public List<String> getTags() {
        return tags;
    }

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

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
