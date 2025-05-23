package com.pricecomparator.pricecomparator.controller;

import com.pricecomparator.pricecomparator.dto.*;
import com.pricecomparator.pricecomparator.service.BasketOptimizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/basket")
@RequiredArgsConstructor
public class BasketController {
    private final BasketOptimizationService optimizationService;

    @PostMapping("/optimize")
    public OptimizedBasketDto optimizeBasket(@RequestBody BasketRequestDto basketRequestDto) {
        return optimizationService.getBestStoreForBasket(basketRequestDto);
    }
}