package com.pricecomparator.pricecomparator.service;

import com.pricecomparator.pricecomparator.model.PriceAlert;
import com.pricecomparator.pricecomparator.model.Product;
import com.pricecomparator.pricecomparator.repository.PriceAlertRepository;
import com.pricecomparator.pricecomparator.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AlertService {
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final PriceAlertRepository priceAlertRepository;
    @Scheduled(fixedRate = 3600000)
    public void checkAlerts() {
        List<PriceAlert> alerts = priceAlertRepository.findAll();
        for (PriceAlert alert : alerts) {
            Product product = productRepository.findByProductId(alert.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            double currentPrice = productService.getCurrentPrice(product.getProductId());

            if (currentPrice <= alert.getTargetPrice() && !alert.isNotified()) {
                alert.setNotified(true);
                priceAlertRepository.save(alert);
            }
        }
    }

}
