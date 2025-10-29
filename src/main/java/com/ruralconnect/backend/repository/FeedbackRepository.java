package com.ruralconnect.backend.repository;

import com.ruralconnect.backend.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    // We don't need any custom methods here
    // findAll() is all the admin needs
}