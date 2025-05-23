package com.pricecomparator.pricecomparator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BestDiscountDto {
    private String productId;
    private String productName;
    private String store;
    private double oldPrice;
    private double newPrice;
    private double discountPercentage;
}
