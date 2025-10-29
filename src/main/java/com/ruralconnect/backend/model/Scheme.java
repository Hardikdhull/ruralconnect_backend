package com.ruralconnect.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "schemes")
public class Scheme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    @Lob // For long descriptions
    private String description;

    private String eligibility; // e.g., "Farmers with < 5 acres"

    private String benefits; // What the scheme provides

    private String applyLink; // URL to apply

    private LocalDate lastDateToApply;
}