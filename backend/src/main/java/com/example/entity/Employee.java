package com.example.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employee")
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "nic", unique = true)
    private String nic;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private EmployeeRole role;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "date_joined", nullable = false)
    private LocalDate dateJoined;

    @OneToMany(mappedBy = "cashier", cascade = CascadeType.ALL)
    private List<Payment> payments;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Reading> readings;

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    private List<Report> reports;

    public enum EmployeeRole {
        ADMIN, METER_READER, CASHIER, MANAGER
    }

    // Constructors
    public Employee() {
    }

    public Employee(String fullName, String nic, EmployeeRole role, String username,
            String password, String contactNo, LocalDate dateJoined) {
        this.fullName = fullName;
        this.nic = nic;
        this.role = role;
        this.username = username;
        this.password = password;
        this.contactNo = contactNo;
        this.dateJoined = dateJoined;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public EmployeeRole getRole() {
        return role;
    }

    public void setRole(EmployeeRole role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public LocalDate getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(LocalDate dateJoined) {
        this.dateJoined = dateJoined;
    }

    public List<Reading> getReadings() {
        return readings;
    }

    public void setReadings(List<Reading> readings) {
        this.readings = readings;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}