package com.coral.backend.entities;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "areas")
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToMany(mappedBy = "areas")
    private List<User> user;

    public void setName(String name) {
        this.name = name;
    }
    public String getName(){
        return name;
    }
}