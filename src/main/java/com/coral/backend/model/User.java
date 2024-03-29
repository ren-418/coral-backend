package com.coral.backend.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //TABLE_PER_CLASS o JOINED
@DiscriminatorColumn(name = "user_type")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    private String name;
    private String email;
    private String password;
    private String description;
    private String location;
    private int type;
    private Date initial_date;

    @ManyToMany
    private List<Area> areas;
}
