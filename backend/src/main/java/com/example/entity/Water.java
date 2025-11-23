package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "water")
@PrimaryKeyJoinColumn(name = "utility_id")
public class Water extends UtilityType {

    @Column(name = "unit_type")
    private String unitType;

    public Water() {
    }

    public Water(String utilityName, String unitType) {
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