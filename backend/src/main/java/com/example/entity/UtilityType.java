package com.example.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "utility_type")
@Inheritance(strategy = InheritanceType.JOINED)
public class UtilityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "utility_id")
    private Long id;

    @Column(name = "utility_name", nullable = false)
    private String utilityName;

    @OneToMany(mappedBy = "utilityType", cascade = CascadeType.ALL)
    private List<Meter> meters;

    @OneToMany(mappedBy = "utilityType", cascade = CascadeType.ALL)
    private List<Tariff> tariffs;

    public UtilityType() {
    }

    public UtilityType(String utilityName) {
        this.utilityName = utilityName;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUtilityName() {
        return utilityName;
    }

    public void setUtilityName(String utilityName) {
        this.utilityName = utilityName;
    }

    public List<Meter> getMeters() {
        return meters;
    }

    public void setMeters(List<Meter> meters) {
        this.meters = meters;
    }

    public List<Tariff> getTariffs() {
        return tariffs;
    }

    public void setTariffs(List<Tariff> tariffs) {
        this.tariffs = tariffs;
    }
}