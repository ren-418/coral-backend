package com.coral.backend.entities;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int area_id;
    private String area_name;

    @ManyToMany
    private List<User> user;
}