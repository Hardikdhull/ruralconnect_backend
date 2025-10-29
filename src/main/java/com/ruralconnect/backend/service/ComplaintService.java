package com.ruralconnect.backend.service;

import com.ruralconnect.backend.model.Complaint;
import com.ruralconnect.backend.model.User;
import com.ruralconnect.backend.repository.ComplaintRepository;
import com.ruralconnect.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    // --- FOR USERS ---

    // Create a new complaint
    public Complaint createComplaint(Complaint complaint, String userEmail) {
        // Find the logged-in user
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Link the complaint to the user
        complaint.setUser(user);

        // Note: The @PrePersist in Complaint.java will automatically set the
        // status to "Lodged" and set the created-at date.

        return complaintRepository.save(complaint);
    }

    // Get all complaints for a specific user
    public List<Complaint> getComplaintsByUserEmail(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return complaintRepository.findByUserId(user.getId());
    }

    // --- FOR ADMINS ---

    // Get all complaints from all users
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    // Update a complaint's status
    public Complaint updateComplaintStatus(Long id, Map<String, String> statusUpdate) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        String newStatus = statusUpdate.get("status");
        if (newStatus == null || newStatus.isBlank()) {
            throw new RuntimeException("New status is required");
        }

        // You could add validation here to ensure status is one of
        // "Lodged", "In Progress", or "Resolved"

        complaint.setStatus(newStatus);
        return complaintRepository.save(complaint);
    }
}