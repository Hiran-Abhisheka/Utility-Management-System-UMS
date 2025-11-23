package com.example.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "business")
@PrimaryKeyJoinColumn(name = "customer_id")
public class Business extends Customer {

    @Column(name = "br_number", unique = true)
    private String brNumber;

    @Column(name = "business_name")
    private String businessName;

    public Business() {
    }

    public Business(String fullName, String nic, String contactNo, String email,
            Gender gender, LocalDate dob, String street, String city,
            String district, String brNumber, String businessName) {
        super(fullName, nic, contactNo, email, gender, dob, street, city, district, CustomerType.BUSINESS);
        this.brNumber = brNumber;
        this.businessName = businessName;
    }

    public String getBrNumber() {
        return brNumber;
    }

    public void setBrNumber(String brNumber) {
        this.brNumber = brNumber;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
}