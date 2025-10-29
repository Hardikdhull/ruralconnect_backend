package com.ruralconnect.backend.controller;

import com.ruralconnect.backend.model.Complaint;
import com.ruralconnect.backend.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    // --- USER ENDPOINTS ---

    // POST /api/complaints
    // Create a new complaint
    @PostMapping
    public ResponseEntity<Complaint> createComplaint(@RequestBody Complaint complaint,
                                                     @AuthenticationPrincipal UserDetails userDetails) {
        // @AuthenticationPrincipal automatically gets the logged-in user's details
        String userEmail = userDetails.getUsername();
        Complaint newComplaint = complaintService.createComplaint(complaint, userEmail);
        return new ResponseEntity<>(newComplaint, HttpStatus.CREATED);
    }

    // GET /api/complaints/my-complaints
    // Get all complaints for the logged-in user
    @GetMapping("/my-complaints")
    public ResponseEntity<List<Complaint>> getMyComplaints(@AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        List<Complaint> complaints = complaintService.getComplaintsByUserEmail(userEmail);
        return ResponseEntity.ok(complaints);
    }

    // --- ADMIN ENDPOINTS ---

    // GET /api/complaints
    // Get ALL complaints (for the admin app)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        List<Complaint> complaints = complaintService.getAllComplaints();
        return ResponseEntity.ok(complaints);
    }

    // PUT /api/complaints/{id}/status
    // Update a complaint's status (for the admin app)
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Complaint> updateComplaintStatus(@PathVariable Long id,
                                                           @RequestBody Map<String, String> statusUpdate) {
        // The RequestBody should be a simple JSON: { "status": "In Progress" }
        Complaint updatedComplaint = complaintService.updateComplaintStatus(id, statusUpdate);
        return ResponseEntity.ok(updatedComplaint);
    }
}