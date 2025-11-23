package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "electricity")
@PrimaryKeyJoinColumn(name = "utility_id")
public class Electricity extends UtilityType {

    @Column(name = "unit_type")
    private String unitType;

    public Electricity() {
    }

    public Electricity(String utilityName, String unitType) {
        super(utilityName);
        this.unitType = unitType;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }
}