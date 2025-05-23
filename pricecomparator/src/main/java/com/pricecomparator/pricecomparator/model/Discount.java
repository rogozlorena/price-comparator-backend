package com.pricecomparator.pricecomparator.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "discounts")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private String store;

    private double oldPrice;
    private double newPrice;

    private LocalDate startDate;
    private LocalDate endDate;

    public double getDiscountPercentage() {
        if (oldPrice <= 0) return 0;
        return ((oldPrice - newPrice) / oldPrice) * 100;
    }
}
