package com.ruralconnect.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data; // Or generate getters/setters manually

@Data // Lombok annotation for automatic getters, setters, toString, etc.
@Entity // Tells Spring Data JPA this is a database table
@Table(name = "users") // Specifies the actual table name
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementing ID
    private Long id;

    @NotBlank // Validation: cannot be null or empty
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    @Email // Validation: must be a valid email format
    @Column(unique = true) // Database constraint: no two users can have the same email
    private String email;

    @NotBlank
    @Size(min = 8) // We will store a HASHED password, not the plain text
    private String password;

    @NotBlank
    private String phoneNumber;

    // This is CRITICAL for security
    // We can store "ROLE_USER" or "ROLE_ADMIN"
    @NotBlank
    private String role;

    private String address;

    // You can add other fields like 'city', 'pincode', etc.
}