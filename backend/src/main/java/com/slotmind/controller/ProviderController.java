package com.slotmind.controller;

import com.slotmind.model.Provider;
import com.slotmind.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/providers")
@CrossOrigin(origins = "http://localhost:4200")
public class ProviderController {
    
    @Autowired
    private ProviderService providerService;
    
    @GetMapping
    public List<Provider> getAllProviders() {
        return providerService.getAllProviders();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Provider> getProviderById(@PathVariable String id) {
        Optional<Provider> provider = providerService.getProviderById(id);
        if (provider.isPresent()) {
            return ResponseEntity.ok(provider.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public Provider createProvider(@RequestBody Provider provider) {
        return providerService.createProvider(provider);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Provider> updateProvider(@PathVariable String id, @RequestBody Provider providerDetails) {
        Provider updatedProvider = providerService.updateProvider(id, providerDetails);
        if (updatedProvider != null) {
            return ResponseEntity.ok(updatedProvider);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable String id) {
        providerService.deleteProvider(id);
        return ResponseEntity.noContent().build();
    }
}