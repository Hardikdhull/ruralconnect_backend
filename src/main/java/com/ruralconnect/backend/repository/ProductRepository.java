package com.ruralconnect.backend.repository;

import com.ruralconnect.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // You can add custom finders later, like:
    // List<Product> findByCategory(String category);
}