package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tariff")
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tariffId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "utility_type_id")
    private UtilityType utilityType;

    private Double minUnit;
    private Double maxUnit;
    private Double ratePerUnit;
    private LocalDate effectiveFrom;

    // Getters and Setters
    public Long getTariffId() {
        return tariffId;
    }

    public void setTariffId(Long tariffId) {
        this.tariffId = tariffId;
    }

    public UtilityType getUtilityType() {
        return utilityType;
    }

    public void setUtilityType(UtilityType utilityType) {
        this.utilityType = utilityType;
    }

    public Double getMinUnit() {
        return minUnit;
    }

    public void setMinUnit(Double minUnit) {
        this.minUnit = minUnit;
    }

    public Double getMaxUnit() {
        return maxUnit;
    }

    public void setMaxUnit(Double maxUnit) {
        this.maxUnit = maxUnit;
    }

    public Double getRatePerUnit() {
        return ratePerUnit;
    }

    public void setRatePerUnit(Double ratePerUnit) {
        this.ratePerUnit = ratePerUnit;
    }

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDate effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }
}