package com.pricecomparator.pricecomparator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewDiscountDto {
    private String productId;
    private String productName;
    private String store;
    private double oldPrice;
    private double newPrice;
    private LocalDate startDate;
}
