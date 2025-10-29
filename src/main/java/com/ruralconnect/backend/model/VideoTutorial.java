package com.ruralconnect.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "video_tutorials")
public class VideoTutorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @Lob
    private String description;

    @NotBlank
    @Column(nullable = false)
    private String videoUrl; // A URL to a YouTube or similar video
}