package com.pricecomparator.pricecomparator.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private String store;
    private LocalDate date;
    private double value;
    public Price(Product product, String store, LocalDate date, double value) {
        this.product = product;
        this.store = store;
        this.date = date;
        this.value = value;
    }

}
