package com.ruralconnect.backend.controller;

import com.ruralconnect.backend.model.Scheme;
import com.ruralconnect.backend.service.SchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schemes")
public class SchemeController {

    @Autowired
    private SchemeService schemeService;

    // ✅ PUBLIC: Anyone can see all schemes
    @GetMapping
    public List<Scheme> getAllSchemes() {
        return schemeService.getAllSchemes();
    }

    // ✅ PUBLIC: Anyone can see a single scheme
    @GetMapping("/{id}")
    public ResponseEntity<Scheme> getSchemeById(@PathVariable Long id) {
        return schemeService.getSchemeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔒 ADMIN ONLY
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Scheme createScheme(@RequestBody Scheme scheme) {
        return schemeService.createScheme(scheme);
    }

    // 🔒 ADMIN ONLY
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Scheme> updateScheme(@PathVariable Long id, @RequestBody Scheme schemeDetails) {
        try {
            return ResponseEntity.ok(schemeService.updateScheme(id, schemeDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 🔒 ADMIN ONLY
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteScheme(@PathVariable Long id) {
        try {
            schemeService.deleteScheme(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}