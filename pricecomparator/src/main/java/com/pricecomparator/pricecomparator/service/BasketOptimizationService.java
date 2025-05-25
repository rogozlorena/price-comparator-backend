package com.pricecomparator.pricecomparator.service;

import com.pricecomparator.pricecomparator.dto.*;
import com.pricecomparator.pricecomparator.model.Price;
import com.pricecomparator.pricecomparator.model.Product;
import com.pricecomparator.pricecomparator.repository.PriceRepository;
import com.pricecomparator.pricecomparator.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasketOptimizationService {
    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;

    public OptimizedBasketDto getBestStoreForBasket(BasketRequestDto basketRequestDto) {
        List<Product> products = basketRequestDto.getProductIds().stream()
                .map(productRepository::findByProductId)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        // Map<store, List<Price>>
        Map<String, List<Price>> storePriceMap = new HashMap<>();

        for (Product product : products) {
            List<Price> prices = priceRepository.findByProduct(product);
            for (Price price : prices) {
                storePriceMap.computeIfAbsent(price.getStore(), k -> new ArrayList<>()).add(price);
            }
        }

        String bestStore = null;
        double bestTotal = Double.MAX_VALUE;
        List<OptimizedProductDto> bestProducts = new ArrayList<>();

        for (Map.Entry<String, List<Price>> entry : storePriceMap.entrySet()) {
            String store = entry.getKey();
            List<Price> priceList = entry.getValue();

            Map<String, Price> productMap = priceList.stream()
                    .collect(Collectors.toMap(p -> p.getProduct().getProductId(), p -> p, (p1, p2) -> p1));

            double total = 0.0;
            List<OptimizedProductDto> currentProducts = new ArrayList<>();
            boolean allAvailable = true;

            for (Product p : products) {
                Price price = productMap.get(p.getProductId());
                if (price == null) {
                    allAvailable = false;
                    break;
                }
                total += price.getValue();
                currentProducts.add(new OptimizedProductDto(p.getProductId(), p.getName(), price.getValue()));
            }

            if (allAvailable && total < bestTotal) {
                bestStore = store;
                bestTotal = total;
                bestProducts = currentProducts;
            }
        }

        return new OptimizedBasketDto(bestStore, bestTotal, bestProducts);
    }
}
