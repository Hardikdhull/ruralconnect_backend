package com.ruralconnect.backend.repository;

import com.ruralconnect.backend.model.Scheme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchemeRepository extends JpaRepository<Scheme, Long> {
    // ...
}