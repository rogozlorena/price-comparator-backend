package com.pricecomparator.pricecomparator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class OptimizedBasketDto {
    private String store;
    private double totalPrice;
    private List<OptimizedProductDto> products;
}

