package com.ruralconnect.backend.service;

import com.ruralconnect.backend.model.Product;
import com.ruralconnect.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // GET all products (for users and admins)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // GET one product by ID (for users and admins)
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // POST a new product (ADMIN ONLY)
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // PUT (update) a product (ADMIN ONLY)
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setCategory(productDetails.getCategory());
        product.setImageUrl(productDetails.getImageUrl());
        product.setStockQuantity(productDetails.getStockQuantity());

        return productRepository.save(product);
    }

    // DELETE a product (ADMIN ONLY)
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        productRepository.delete(product);
    }
}