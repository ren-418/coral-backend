package com.coral.backend.entities;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //TABLE_PER_CLASS o JOINED
@DiscriminatorColumn(name = "userType")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
    private String description;
    private String location;
    private Date initial_date;

    public User(){}

    @ManyToMany
    private List<Area> areas;

    //Setters
    public void setUserId(long user_id) {
        this.userId = user_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setInitialDate(Date initial_date) {
        this.initial_date = initial_date;
    }

    public void setAreas(Area area) {
        this.areas.add(area);
    }

    //Getters
    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public Date getInitialDate() {
        return initial_date;
    }

    public List<Area> getAreas() {
        return areas;
    }
}
