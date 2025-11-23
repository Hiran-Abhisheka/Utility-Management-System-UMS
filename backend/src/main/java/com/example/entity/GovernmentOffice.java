package com.example.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "government_office")
@PrimaryKeyJoinColumn(name = "customer_id")
public class GovernmentOffice extends Customer {

    @Column(name = "office_code", unique = true)
    private String officeCode;

    @Column(name = "office_name")
    private String officeName;

    public GovernmentOffice() {
    }

    public GovernmentOffice(String fullName, String nic, String contactNo, String email,
            Gender gender, LocalDate dob, String street, String city,
            String district, String officeCode, String officeName) {
        super(fullName, nic, contactNo, email, gender, dob, street, city, district, CustomerType.GOVERNMENT);
        this.officeCode = officeCode;
        this.officeName = officeName;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }
}