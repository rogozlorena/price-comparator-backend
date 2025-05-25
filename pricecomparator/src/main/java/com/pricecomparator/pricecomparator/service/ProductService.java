package com.pricecomparator.pricecomparator.service;

import com.pricecomparator.pricecomparator.dto.ProductRecommendationDto;
import com.pricecomparator.pricecomparator.model.Product;
import com.pricecomparator.pricecomparator.repository.PriceRepository;
import com.pricecomparator.pricecomparator.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.pricecomparator.pricecomparator.model.Price;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;
    public double getCurrentPrice(String productId) {
        return priceRepository.findTopByProduct_ProductIdOrderByDateDesc(productId)
                .map(Price::getValue)
                .orElse(0.0);
    }
    public List<ProductRecommendationDto> getProductRecommendations(String productId) {
        Product target = productRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<Product> similar = productRepository.findByCategory(target.getCategory());

        return similar.stream()
                .filter(p -> !p.getProductId().equals(productId))
                .map(p -> {
                    double pricePerUnit = getCurrentPrice(p.getProductId()) / p.getGrammage();
                    return new ProductRecommendationDto(p.getProductId(), p.getName(), pricePerUnit);
                })
                .sorted(Comparator.comparingDouble(ProductRecommendationDto::getPricePerUnit))
                .collect(Collectors.toList());
    }
  /*  private double getCurrentPrice(String productId) {
        return priceRepository.findTopByProduct_ProductIdOrderByDateDesc(productId)
                .map(price -> price.getValue())
                .orElse(0.0);
    }

   */
}
