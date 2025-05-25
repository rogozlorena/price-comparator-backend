package com.pricecomparator.pricecomparator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRecommendationDto {
    private String productId;
    private String productName;
    private double pricePerUnit;
}
