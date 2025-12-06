package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.YearMonth;

@Entity
@Table(name = "reading")
public class Reading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_id")
    private Long id;

    @Column(name = "reading_date", nullable = false)
    private LocalDate readingDate;

    @Column(name = "reading_month", nullable = false)
    private YearMonth readingMonth;

    @Column(name = "current_reading", nullable = false)
    private Double currentReading;

    @Column(name = "previous_reading")
    private Double previousReading;

    @Column(name = "units_consumed")
    private Double unitsConsumed; // Derived: currentReading - previousReading

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meter_id", nullable = false)
    private Meter meter;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee; // MeterReader

    @OneToOne(mappedBy = "reading", cascade = CascadeType.ALL)
    private Bill bill;

    // Constructors
    public Reading() {
    }

    public Reading(LocalDate readingDate, YearMonth readingMonth, Double currentReading,
            Double previousReading, Meter meter, Employee employee) {
        this.readingDate = readingDate;
        this.readingMonth = readingMonth;
        this.currentReading = currentReading;
        this.previousReading = previousReading;
        this.meter = meter;
        this.employee = employee;
        this.unitsConsumed = (currentReading != null && previousReading != null) ? currentReading - previousReading
                : null;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getReadingDate() {
        return readingDate;
    }

    public void setReadingDate(LocalDate readingDate) {
        this.readingDate = readingDate;
    }

    public YearMonth getReadingMonth() {
        return readingMonth;
    }

    public void setReadingMonth(YearMonth readingMonth) {
        this.readingMonth = readingMonth;
    }

    public Double getCurrentReading() {
        return currentReading;
    }

    public void setCurrentReading(Double currentReading) {
        this.currentReading = currentReading;
        calculateUnitsConsumed();
    }

    public Double getPreviousReading() {
        return previousReading;
    }

    public void setPreviousReading(Double previousReading) {
        this.previousReading = previousReading;
        calculateUnitsConsumed();
    }

    public Double getUnitsConsumed() {
        return unitsConsumed;
    }

    public void setUnitsConsumed(Double unitsConsumed) {
        this.unitsConsumed = unitsConsumed;
    }

    public Meter getMeter() {
        return meter;
    }

    public void setMeter(Meter meter) {
        this.meter = meter;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    // Helper method to calculate units consumed
    private void calculateUnitsConsumed() {
        if (currentReading != null && previousReading != null) {
            this.unitsConsumed = currentReading - previousReading;
        }
    }
}