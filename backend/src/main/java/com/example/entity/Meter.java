package com.example.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "meter")
public class Meter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meter_id")
    private Long id;

    @Column(name = "meter_number", unique = true, nullable = false)
    private String meterNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MeterStatus status;

    @Column(name = "installation_date")
    private LocalDate installationDate;

    @Column(name = "connection_date")
    private LocalDate connectionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utility_id", nullable = false)
    private UtilityType utilityType;

    @OneToMany(mappedBy = "meter", cascade = CascadeType.ALL)
    private List<Reading> readings;

    @OneToMany(mappedBy = "meter", cascade = CascadeType.ALL)
    private List<Bill> bills;

    public enum MeterStatus {
        ACTIVE, INACTIVE, MAINTENANCE, DISCONNECTED
    }

    // Constructors
    public Meter() {
    }

    public Meter(String meterNumber, MeterStatus status, LocalDate installationDate,
            LocalDate connectionDate, Customer customer, UtilityType utilityType) {
        this.meterNumber = meterNumber;
        this.status = status;
        this.installationDate = installationDate;
        this.connectionDate = connectionDate;
        this.customer = customer;
        this.utilityType = utilityType;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }

    public MeterStatus getStatus() {
        return status;
    }

    public void setStatus(MeterStatus status) {
        this.status = status;
    }

    public LocalDate getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(LocalDate installationDate) {
        this.installationDate = installationDate;
    }

    public LocalDate getConnectionDate() {
        return connectionDate;
    }

    public void setConnectionDate(LocalDate connectionDate) {
        this.connectionDate = connectionDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public UtilityType getUtilityType() {
        return utilityType;
    }

    public void setUtilityType(UtilityType utilityType) {
        this.utilityType = utilityType;
    }

    public List<Reading> getReadings() {
        return readings;
    }

    public void setReadings(List<Reading> readings) {
        this.readings = readings;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }
}