package com.ruralconnect.backend.controller;

import com.ruralconnect.backend.model.VideoTutorial;
import com.ruralconnect.backend.repository.VideoTutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tutorials")
public class VideoTutorialController {

    @Autowired
    private VideoTutorialRepository videoTutorialRepository;

    // --- PUBLIC ENDPOINT ---
    // GET /api/tutorials
    // Get all tutorials (for the user app)
    @GetMapping
    public List<VideoTutorial> getAllTutorials() {
        return videoTutorialRepository.findAll();
    }

    // --- ADMIN ENDPOINTS ---

    // POST /api/tutorials
    // Create a new tutorial (for the admin app)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public VideoTutorial createTutorial(@RequestBody VideoTutorial tutorial) {
        return videoTutorialRepository.save(tutorial);
    }

    // PUT /api/tutorials/{id}
    // Update a tutorial (for the admin app)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VideoTutorial> updateTutorial(@PathVariable Long id, @RequestBody VideoTutorial tutorialDetails) {
        VideoTutorial tutorial = videoTutorialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutorial not found"));

        tutorial.setTitle(tutorialDetails.getTitle());
        tutorial.setDescription(tutorialDetails.getDescription());
        tutorial.setVideoUrl(tutorialDetails.getVideoUrl());
        return ResponseEntity.ok(videoTutorialRepository.save(tutorial));
    }

    // DELETE /api/tutorials/{id}
    // Delete a tutorial (for the admin app)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTutorial(@PathVariable Long id) {
        VideoTutorial tutorial = videoTutorialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutorial not found"));

        videoTutorialRepository.delete(tutorial);
        return ResponseEntity.noContent().build();
    }
}