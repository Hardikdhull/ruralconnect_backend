package com.ruralconnect.backend.service;

import com.ruralconnect.backend.model.Feedback;
import com.ruralconnect.backend.model.User;
import com.ruralconnect.backend.repository.FeedbackRepository;
import com.ruralconnect.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    // --- FOR USERS ---
    // Create new feedback
    public Feedback createFeedback(Feedback feedback, String userEmail) {
        // Find the logged-in user
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Link the feedback to the user and save it
        feedback.setUser(user);
        return feedbackRepository.save(feedback);
    }

    // --- FOR ADMINS ---
    // Get all feedback
    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }
}