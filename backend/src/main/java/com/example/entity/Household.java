package com.example.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "household")
@PrimaryKeyJoinColumn(name = "customer_id")
public class Household extends Customer {

    @Column(name = "family_size")
    private Integer familySize;

    public Household() {
    }

    public Household(String fullName, String nic, String contactNo, String email,
            Gender gender, LocalDate dob, String street, String city,
            String district, Integer familySize) {
        super(fullName, nic, contactNo, email, gender, dob, street, city, district, CustomerType.HOUSEHOLD);
        this.familySize = familySize;
    }

    public Integer getFamilySize() {
        return familySize;
    }

    public void setFamilySize(Integer familySize) {
        this.familySize = familySize;
    }
}