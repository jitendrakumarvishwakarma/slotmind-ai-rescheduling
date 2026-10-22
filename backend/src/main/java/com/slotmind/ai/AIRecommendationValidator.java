package com.slotmind.ai;

import com.slotmind.model.Slot;
import com.slotmind.model.SlotStatus;
import com.slotmind.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AIRecommendationValidator {
    
    @Autowired
    private SlotRepository slotRepository;
    
    /**
     * Validate an AI recommendation to ensure it's safe to use
     * @param recommendation The AI recommendation to validate
     * @return true if the recommendation is valid, false otherwise
     */
    public boolean isValidRecommendation(AIRecommendation recommendation) {
        if (recommendation == null) {
            return false;
        }
        
        Slot recommendedSlot = recommendation.getRecommendedSlot();
        
        // Check if slot exists in database
        if (recommendedSlot == null || recommendedSlot.getId() == null) {
            return false;
        }
        
        // Check if slot is still in the database
        if (!slotRepository.existsById(recommendedSlot.getId())) {
            return false;
        }
        
        // Refetch slot from database to get current state
        Slot dbSlot = slotRepository.findById(recommendedSlot.getId()).orElse(null);
        if (dbSlot == null) {
            return false;
        }
        
        // Check if slot is still open
        if (dbSlot.getStatus() != SlotStatus.OPEN) {
            return false;
        }
        
        // Check if slot is in the future
        if (dbSlot.getStartTime().isBefore(LocalDateTime.now())) {
            return false;
        }
        
        // Check confidence score is within valid range
        if (recommendation.getConfidenceScore() < 0.0 || recommendation.getConfidenceScore() > 1.0) {
            return false;
        }
        
        // Check if explanation is provided
        if (recommendation.getExplanation() == null || recommendation.getExplanation().trim().isEmpty()) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Enhance an AI recommendation with additional validation and metadata
     * @param recommendation The AI recommendation to enhance
     * @return The enhanced recommendation
     */
    public AIRecommendation enhanceRecommendation(AIRecommendation recommendation) {
        if (recommendation == null) {
            return null;
        }
        
        // Refetch slot from database to get current state
        if (recommendation.getRecommendedSlot() != null && recommendation.getRecommendedSlot().getId() != null) {
            Slot dbSlot = slotRepository.findById(recommendation.getRecommendedSlot().getId()).orElse(null);
            if (dbSlot != null) {
                recommendation.getRecommendedSlot().setStatus(dbSlot.getStatus());
                recommendation.getRecommendedSlot().setStartTime(dbSlot.getStartTime());
            }
        }
        
        return recommendation;
    }
}