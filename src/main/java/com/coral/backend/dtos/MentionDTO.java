package com.coral.backend.dtos;

public class MentionDTO {

    private String name;

    private String type;

    private String profileImage;

    //Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    //Getters

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
