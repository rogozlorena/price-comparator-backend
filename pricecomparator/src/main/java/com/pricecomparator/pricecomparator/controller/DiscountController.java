package com.pricecomparator.pricecomparator.controller;

import com.pricecomparator.pricecomparator.dto.BestDiscountDto;
import com.pricecomparator.pricecomparator.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;


    @GetMapping("/api/discounts/best")
    public List<BestDiscountDto> getBestDiscounts(@RequestParam(defaultValue = "10") int limit) {
        return discountService.getBestDiscounts(limit);
    }

    @GetMapping("/api/discounts/new")
    public List<BestDiscountDto> getNewDiscounts(@RequestParam(defaultValue = "10") int limit) {
        return discountService.getNewDiscounts(limit);
    }


}
