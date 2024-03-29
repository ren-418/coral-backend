package com.coral.backend.model;
import jakarta.persistence.*;

@Entity
public class Area {
    private int area_id;
    private String area_name;
    private User user;
}
