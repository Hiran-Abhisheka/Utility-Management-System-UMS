package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "gas")
@PrimaryKeyJoinColumn(name = "utility_id")
public class Gas extends UtilityType {

    @Column(name = "unit_type")
    private String unitType;

    public Gas() {
    }

    public Gas(String utilityName, String unitType) {
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