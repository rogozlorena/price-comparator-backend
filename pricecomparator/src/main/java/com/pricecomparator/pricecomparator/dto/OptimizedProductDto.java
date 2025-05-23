package com.pricecomparator.pricecomparator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptimizedProductDto {
    private String productId;
    private String productName;
    private double price;
}

