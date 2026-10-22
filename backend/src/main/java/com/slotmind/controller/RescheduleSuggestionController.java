package com.slotmind.controller;

import com.slotmind.dto.RescheduleSuggestionDTO;
import com.slotmind.model.RescheduleSuggestion;
import com.slotmind.service.RescheduleSuggestionService;
import com.slotmind.service.RescheduleSuggestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reschedule-suggestions")
@CrossOrigin(origins = "http://localhost:4200")
public class RescheduleSuggestionController {
    
    @Autowired
    private RescheduleSuggestionService rescheduleSuggestionService;
    
    @Autowired
    private RescheduleSuggestionMapper rescheduleSuggestionMapper;
    
    @GetMapping
    public List<RescheduleSuggestionDTO> getAllSuggestions() {
        return rescheduleSuggestionService.getAllSuggestions().stream()
            .map(rescheduleSuggestionMapper::toDTO)
            .collect(Collectors.toList());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RescheduleSuggestionDTO> getSuggestionById(@PathVariable Long id) {
        Optional<RescheduleSuggestion> suggestion = rescheduleSuggestionService.getSuggestionById(id);
        if (suggestion.isPresent()) {
            return ResponseEntity.ok(rescheduleSuggestionMapper.toDTO(suggestion.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public RescheduleSuggestionDTO createSuggestion(@RequestBody RescheduleSuggestion suggestion) {
        RescheduleSuggestion createdSuggestion = rescheduleSuggestionService.createSuggestion(suggestion);
        return rescheduleSuggestionMapper.toDTO(createdSuggestion);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<RescheduleSuggestionDTO> updateSuggestion(@PathVariable Long id, @RequestBody RescheduleSuggestion suggestionDetails) {
        RescheduleSuggestion updatedSuggestion = rescheduleSuggestionService.updateSuggestion(id, suggestionDetails);
        if (updatedSuggestion != null) {
            return ResponseEntity.ok(rescheduleSuggestionMapper.toDTO(updatedSuggestion));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSuggestion(@PathVariable Long id) {
        rescheduleSuggestionService.deleteSuggestion(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/generate")
    public ResponseEntity<RescheduleSuggestionDTO> generateSuggestion(
            @RequestParam String appointmentId,
            @RequestParam String triggerReason) {
        RescheduleSuggestion suggestion = rescheduleSuggestionService.generateRescheduleSuggestion(appointmentId, triggerReason);
        if (suggestion != null) {
            return ResponseEntity.ok(rescheduleSuggestionMapper.toDTO(suggestion));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/{id}/accept")
    public ResponseEntity<RescheduleSuggestionDTO> acceptSuggestion(@PathVariable Long id) {
        RescheduleSuggestion suggestion = rescheduleSuggestionService.acceptSuggestion(id);
        if (suggestion != null) {
            return ResponseEntity.ok(rescheduleSuggestionMapper.toDTO(suggestion));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/{id}/reject")
    public ResponseEntity<RescheduleSuggestionDTO> rejectSuggestion(@PathVariable Long id) {
        RescheduleSuggestion suggestion = rescheduleSuggestionService.rejectSuggestion(id);
        if (suggestion != null) {
            return ResponseEntity.ok(rescheduleSuggestionMapper.toDTO(suggestion));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}