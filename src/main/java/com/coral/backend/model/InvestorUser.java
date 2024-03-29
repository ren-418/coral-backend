package com.coral.backend.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class InvestorUser extends User {
    private int investor_type;
    private String investment_criteria;
    private int range_min;
    private int range_max;
}
