package com.example.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "manager")
@PrimaryKeyJoinColumn(name = "employee_id")
public class Manager extends Employee {

    public Manager() {
    }

    public Manager(String fullName, String nic, String username, String password,
            String contactNo, LocalDate dateJoined) {
        super(fullName, nic, EmployeeRole.MANAGER, username, password, contactNo, dateJoined);
    }
}