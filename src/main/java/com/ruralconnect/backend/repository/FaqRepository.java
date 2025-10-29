package com.ruralconnect.backend.repository;

import com.ruralconnect.backend.model.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<Faq, Long> {
}