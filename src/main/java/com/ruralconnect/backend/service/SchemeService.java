package com.ruralconnect.backend.service;

import com.ruralconnect.backend.model.Scheme;
import com.ruralconnect.backend.repository.SchemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchemeService {

    @Autowired
    private SchemeRepository schemeRepository;

    // GET all schemes
    public List<Scheme> getAllSchemes() {
        return schemeRepository.findAll();
    }

    // GET one scheme by ID
    public Optional<Scheme> getSchemeById(Long id) {
        return schemeRepository.findById(id);
    }

    // POST a new scheme (ADMIN ONLY)
    public Scheme createScheme(Scheme scheme) {
        return schemeRepository.save(scheme);
    }

    // PUT (update) a scheme (ADMIN ONLY)
    public Scheme updateScheme(Long id, Scheme schemeDetails) {
        Scheme scheme = schemeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scheme not found with id: " + id));

        scheme.setTitle(schemeDetails.getTitle());
        scheme.setDescription(schemeDetails.getDescription());
        scheme.setEligibility(schemeDetails.getEligibility());
        scheme.setBenefits(schemeDetails.getBenefits());
        scheme.setApplyLink(schemeDetails.getApplyLink());
        scheme.setLastDateToApply(schemeDetails.getLastDateToApply());

        return schemeRepository.save(scheme);
    }

    // DELETE a scheme (ADMIN ONLY)
    public void deleteScheme(Long id) {
        Scheme scheme = schemeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scheme not found with id: " + id));
        schemeRepository.delete(scheme);
    }
}