package com.ruralconnect.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // This links the complaint to the user who submitted it
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    private String name; // "Enter your name"

    @NotBlank
    private String contactNumber; // "Enter your contact number"

    @NotBlank
    private String complaintType; // "Complaint Type" (e.g., Electricity)

    @NotBlank
    private String location; // "Enter your location"

    @NotBlank
    @Lob // Use @Lob for long text fields
    private String complaintText; // "Enter your complaint"

    // We will store a URL to the uploaded image, not the image itself
    private String imageUrl;

    @Column(nullable = false)
    private String status; // "Lodged", "In Progress", "Resolved"

    private LocalDateTime createdAt;

    // This automatically sets the status and date when a new complaint is created
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        status = "Lodged"; // Matches your "Complaint Status" screen
    }
}