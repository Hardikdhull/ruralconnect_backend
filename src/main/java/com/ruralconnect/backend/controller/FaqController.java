package com.ruralconnect.backend.controller;

import com.ruralconnect.backend.model.Faq;
import com.ruralconnect.backend.repository.FaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faqs")
public class FaqController {

    @Autowired
    private FaqRepository faqRepository;

    // --- PUBLIC ENDPOINT ---
    // GET /api/faqs
    // Get all FAQs (for the user app)
    @GetMapping
    public List<Faq> getAllFaqs() {
        return faqRepository.findAll();
    }

    // --- ADMIN ENDPOINTS ---

    // POST /api/faqs
    // Create a new FAQ (for the admin app)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Faq createFaq(@RequestBody Faq faq) {
        return faqRepository.save(faq);
    }

    // PUT /api/faqs/{id}
    // Update an existing FAQ (for the admin app)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Faq> updateFaq(@PathVariable Long id, @RequestBody Faq faqDetails) {
        Faq faq = faqRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FAQ not found"));

        faq.setQuestion(faqDetails.getQuestion());
        faq.setAnswer(faqDetails.getAnswer());
        return ResponseEntity.ok(faqRepository.save(faq));
    }

    // DELETE /api/faqs/{id}
    // Delete an FAQ (for the admin app)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteFaq(@PathVariable Long id) {
        Faq faq = faqRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FAQ not found"));

        faqRepository.delete(faq);
        return ResponseEntity.noContent().build();
    }
}