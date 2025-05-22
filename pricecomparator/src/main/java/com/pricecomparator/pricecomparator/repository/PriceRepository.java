package com.pricecomparator.pricecomparator.repository;

import com.pricecomparator.pricecomparator.model.Price;
import com.pricecomparator.pricecomparator.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    List<Price> findByProduct(Product product);
    List<Price> findByStore(String store);
    List<Price> findByDate(LocalDate date);
}
