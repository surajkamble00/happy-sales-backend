package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "purchases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String farmerName;

    @Column(nullable = false)
    private String commodity;

    @Column(nullable = false)
    private Double rawQuantityKg;

    @Column(nullable = false)
    private BigDecimal ratePerKg;

    @Column(nullable = false)
    private BigDecimal transportationCost;

    @Column(nullable = false)
    private BigDecimal totalLandedCost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PurchasePaymentStatus paymentStatus;

    private LocalDate purchaseDate;
}