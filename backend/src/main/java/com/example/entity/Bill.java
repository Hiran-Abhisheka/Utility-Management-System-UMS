package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private Long id;

    private LocalDate billDate;
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private BillStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "meter_id")
    private Meter meter;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "reading_id")
    private Reading reading;

    @JsonIgnore
    @OneToMany(mappedBy = "bill")
    private List<Payment> payments;

    // Derived attributes
    @Transient
    public Double getAmount() {
        if (reading != null && reading.getMeter() != null) {
            // Calculate based on tariff and units consumed
            // This would need tariff lookup logic
            return reading.getUnitsConsumed() * 10.0; // Placeholder rate
        }
        return 0.0;
    }

    @Transient
    public Double getOutstandingBalance() {
        Double totalPaid = payments != null ? payments.stream()
                .mapToDouble(Payment::getAmountPaid)
                .sum() : 0.0;
        return getAmount() - totalPaid;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }

    public Meter getMeter() {
        return meter;
    }

    public void setMeter(Meter meter) {
        this.meter = meter;
    }

    public Reading getReading() {
        return reading;
    }

    public void setReading(Reading reading) {
        this.reading = reading;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}