package com.pricecomparator.pricecomparator.repository;

import com.pricecomparator.pricecomparator.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductId(String productId);
    List<Product> findByCategory(String category);

}

