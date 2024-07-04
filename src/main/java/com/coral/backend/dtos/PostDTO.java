package com.coral.backend.dtos;

import java.util.List;
import java.util.Map;

public class PostDTO {
    private long id;
    private String enterpriseName;
    private long enterpriseUserId;
    private String enterpriseProfileImage;
    private String title;
    private String description;
    private String image;
    private String date;
    private List<NameAndIdDTO> tags;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getEnterpriseName() {
        return enterpriseName;
    }
    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
    public long getEnterpriseUserId() {
        return enterpriseUserId;
    }
    public void setEnterpriseUserId(long enterpriseUserId) {
        this.enterpriseUserId = enterpriseUserId;
    }
    public String getEnterpriseProfileImage() {
        return enterpriseProfileImage;
    }
    public void setEnterpriseProfileImage(String enterpriseProfileImage) {
        this.enterpriseProfileImage = enterpriseProfileImage;
    }
    public List<NameAndIdDTO> getTags() {
        return tags;
    }
    public void setTags(List<NameAndIdDTO> tags) {
        this.tags = tags;
    }
}
