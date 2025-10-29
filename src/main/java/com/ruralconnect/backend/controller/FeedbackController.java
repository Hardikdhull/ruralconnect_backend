package com.ruralconnect.backend.controller;

import com.ruralconnect.backend.model.Feedback;
import com.ruralconnect.backend.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    // --- USER ENDPOINT ---
    // POST /api/feedback
    // Create new feedback
    @PostMapping
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback,
                                                   @AuthenticationPrincipal UserDetails userDetails) {
        // @AuthenticationPrincipal gets the logged-in user's email
        String userEmail = userDetails.getUsername();
        Feedback newFeedback = feedbackService.createFeedback(feedback, userEmail);
        return new ResponseEntity<>(newFeedback, HttpStatus.CREATED);
    }

    // --- ADMIN ENDPOINT ---
    // GET /api/feedback
    // Get ALL feedback (for the admin app)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        List<Feedback> allFeedback = feedbackService.getAllFeedback();
        return ResponseEntity.ok(allFeedback);
    }
}