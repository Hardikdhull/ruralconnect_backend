package com.ruralconnect.backend.repository;

import com.ruralconnect.backend.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    // A custom query to find all complaints for a specific user
    // This will power the user's "My Complaints" list
    List<Complaint> findByUserId(Long userId);
}