package com.coral.backend.entities;

import jakarta.persistence.*;


@Entity
public class EnterpriseUser extends User {

    @Column(insertable=false, updatable=false)
    private String userType = "enterprise";

    public String getUserType() {
        return userType;
    }

}
