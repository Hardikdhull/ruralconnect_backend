package com.ruralconnect.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Lob // Specifies a "Large Object" for longer text
    private String description;

    @Positive // Validation: must be a number greater than 0
    private Double price;

    @NotBlank
    private String category; // e.g., "Seeds", "Tools", "Fertilizer"

    private String imageUrl; // URL to the product image

    private int stockQuantity;

    // We can link this to the admin who added it, but let's keep it simple for now
}