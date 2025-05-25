package com.pricecomparator.pricecomparator.service;

import com.pricecomparator.pricecomparator.dto.BestDiscountDto;
import com.pricecomparator.pricecomparator.dto.PriceHistoryDto;
import com.pricecomparator.pricecomparator.model.Discount;
import com.pricecomparator.pricecomparator.model.Price;
import com.pricecomparator.pricecomparator.model.Product;
import com.pricecomparator.pricecomparator.repository.DiscountRepository;
import com.pricecomparator.pricecomparator.repository.PriceRepository;
import com.pricecomparator.pricecomparator.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;

    public List<BestDiscountDto> getBestDiscounts(int limit) {
        LocalDate today = LocalDate.now();


        List<Discount> activeDiscounts = discountRepository.findAll().stream()
                .filter(d -> !d.getStartDate().isAfter(today)) // startDate <= today
                .filter(d -> d.getEndDate() == null || !d.getEndDate().isBefore(today)) // endDate == null sau endDate >= today
                .collect(Collectors.toList());


        return activeDiscounts.stream()
                .sorted(Comparator.comparingDouble(Discount::getDiscountPercentage).reversed())
                .limit(limit)
                .map(d -> new BestDiscountDto(
                        d.getProduct().getProductId(),
                        d.getProduct().getName(),
                        d.getStore(),
                        d.getOldPrice(),
                        d.getNewPrice(),
                        d.getDiscountPercentage()
                ))
                .collect(Collectors.toList());
    }
    public List<BestDiscountDto> getNewDiscounts(int limit) {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);


        List<Discount> newDiscounts = discountRepository.findByStartDateAfter(yesterday);


        return newDiscounts.stream()
                .sorted(Comparator.comparing(Discount::getStartDate).reversed())
                .limit(limit)
                .map(d -> new BestDiscountDto(
                        d.getProduct().getProductId(),
                        d.getProduct().getName(),
                        d.getStore(),
                        d.getOldPrice(),
                        d.getNewPrice(),
                        d.getDiscountPercentage()
                ))
                .collect(Collectors.toList());
    }

    public List<PriceHistoryDto> getPriceHistory(String productId, String store, LocalDate start, LocalDate end) {
        return discountRepository.findByProduct_ProductIdAndStoreAndStartDateBetween(productId, store, start, end)
                .stream()
                .map(d -> new PriceHistoryDto(d.getStartDate(), d.getNewPrice()))
                .collect(Collectors.toList());
    }

    public void generateDiscounts() {

        discountRepository.deleteAll();


        List<Product> products = productRepository.findAll();

        for (Product product : products) {

            List<Price> prices = priceRepository.findByProduct(product).stream()
                    .sorted((p1, p2) -> p2.getDate().compareTo(p1.getDate()))
                    .limit(2)
                    .collect(Collectors.toList());

            if (prices.size() < 2) continue;

            Price latestPrice = prices.get(0);
            Price previousPrice = prices.get(1);

            if (latestPrice.getValue() < previousPrice.getValue()) {
                Discount discount = new Discount();
                discount.setProduct(product);
                discount.setStore(latestPrice.getStore());
                discount.setOldPrice(previousPrice.getValue());
                discount.setNewPrice(latestPrice.getValue());
                discount.setStartDate(latestPrice.getDate());
                discount.setEndDate(null); // poti decide ce sa faci aici

                discountRepository.save(discount);
            }
        }
    }

}
