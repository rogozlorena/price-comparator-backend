package com.pricecomparator.pricecomparator.repository;

import com.pricecomparator.pricecomparator.model.PriceAlert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceAlertRepository extends JpaRepository<PriceAlert, Long> {
    List<PriceAlert> findByProductIdAndNotifiedFalse(String productId);
}

