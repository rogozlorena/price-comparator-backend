package com.pricecomparator.pricecomparator.repository;

import com.pricecomparator.pricecomparator.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {


    List<Discount> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate start, LocalDate end);
    List<Discount> findByStartDateAfter(LocalDate date);
    List<Discount> findByProduct_ProductIdAndStoreAndStartDateBetween(String productId, String store, LocalDate start, LocalDate end);


}
