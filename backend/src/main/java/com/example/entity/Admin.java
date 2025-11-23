package com.example.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "admin")
@PrimaryKeyJoinColumn(name = "employee_id")
public class Admin extends Employee {

    public Admin() {
    }

    public Admin(String fullName, String nic, String username, String password,
            String contactNo, LocalDate dateJoined) {
        super(fullName, nic, EmployeeRole.ADMIN, username, password, contactNo, dateJoined);
    }
}