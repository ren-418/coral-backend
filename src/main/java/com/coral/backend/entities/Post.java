package com.coral.backend.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private EnterpriseUser enterpriseUser;
    private String title;
    private String description;
    private byte[] image;
    private Timestamp createdAt;
    @ManyToMany
    private List<Area> areas;
    private String location;

    public EnterpriseUser getEnterpriseUser() {
        return enterpriseUser;
    }
    public Long getId() {
        return id;
    }
    public void setEnterpriseUser(EnterpriseUser enterpriseUser) {
        this.enterpriseUser = enterpriseUser;
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
    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    public List<Area> getAreas() {
        return areas;
    }
    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}
