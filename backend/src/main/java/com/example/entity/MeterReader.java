package com.example.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "meter_reader")
@PrimaryKeyJoinColumn(name = "employee_id")
public class MeterReader extends Employee {

    public MeterReader() {
    }

    public MeterReader(String fullName, String nic, String username, String password,
            String contactNo, LocalDate dateJoined) {
        super(fullName, nic, EmployeeRole.METER_READER, username, password, contactNo, dateJoined);
    }
}