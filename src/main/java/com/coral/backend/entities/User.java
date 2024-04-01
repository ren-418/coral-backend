package com.coral.backend.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "users")
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
    private Date initial_date;

    @ManyToMany
    private List<Area> areas;
}
