package com.pricecomparator.pricecomparator.controller;

import com.pricecomparator.pricecomparator.model.Price;
import com.pricecomparator.pricecomparator.model.Product;
import com.pricecomparator.pricecomparator.repository.PriceRepository;
import com.pricecomparator.pricecomparator.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
public class PriceController {
    private final PriceRepository priceRepository;
    private final ProductRepository productRepository;

    @GetMapping("/product/{productId}")
    public List<Price> getPricesForProduct(@PathVariable Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return priceRepository.findByProduct(product);
    }

    @GetMapping("/store/{storeName}")
    public List<Price> getPricesByStore(@PathVariable String storeName) {
        return priceRepository.findByStore(storeName);
    }

    @GetMapping("/date/{date}")
    public List<Price> getPricesByDate(@PathVariable String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        return priceRepository.findByDate(parsedDate);
    }
}

