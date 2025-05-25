package com.pricecomparator.pricecomparator.service;

import com.pricecomparator.pricecomparator.dto.BestDiscountDto;
import com.pricecomparator.pricecomparator.dto.PriceHistoryDto;
import com.pricecomparator.pricecomparator.model.Discount;
import com.pricecomparator.pricecomparator.repository.DiscountRepository;
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

    public List<BestDiscountDto> getBestDiscounts(int limit) {
        LocalDate today = LocalDate.now();

        // Obține toate discounturile active azi (start <= today && (end >= today OR end == null))
        List<Discount> activeDiscounts = discountRepository.findAll().stream()
                .filter(d -> !d.getStartDate().isAfter(today)) // startDate <= today
                .filter(d -> d.getEndDate() == null || !d.getEndDate().isBefore(today)) // endDate == null sau endDate >= today
                .collect(Collectors.toList());

        // Sortează după procentul de discount descrescător și limitează rezultatul
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

        // Obține discounturile începute în ultimele 24 ore (începând cu ieri)
        List<Discount> newDiscounts = discountRepository.findByStartDateAfter(yesterday);

        // Sortează după data de start descrescător și limitează rezultatul
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


}
