package com.example.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cashier")
@PrimaryKeyJoinColumn(name = "employee_id")
public class Cashier extends Employee {

    public Cashier() {
    }

    public Cashier(String fullName, String nic, String username, String password,
            String contactNo, LocalDate dateJoined) {
        super(fullName, nic, EmployeeRole.CASHIER, username, password, contactNo, dateJoined);
    }
}