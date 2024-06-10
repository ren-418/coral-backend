package com.coral.backend.dtos;

public class NewsModificationDTO {

    private String sessionToken;
    private String title;
    private String description;
    private String image;
    private long postId;


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

    public long getPostId() {
        return postId;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public void setPostId(long postId) {
        this.postId = postId;
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
