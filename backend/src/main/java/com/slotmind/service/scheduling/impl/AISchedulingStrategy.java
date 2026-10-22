package com.slotmind.service.scheduling.impl;

import com.slotmind.ai.AIRecommendation;
import com.slotmind.ai.service.AIClientService;
import com.slotmind.model.Appointment;
import com.slotmind.model.Slot;
import com.slotmind.service.scheduling.SchedulingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AISchedulingStrategy implements SchedulingStrategy {
    
    @Autowired
    private AIClientService aiClientService;
    
    @Override
    public Optional<Slot> findBestSlot(Appointment appointment, String triggerReason) {
        // Get a validated AI recommendation
        Optional<AIRecommendation> aiRecommendation = aiClientService.getValidatedRecommendation(appointment, triggerReason);
        
        // If we got a valid recommendation, return the recommended slot
        if (aiRecommendation.isPresent()) {
            return Optional.of(aiRecommendation.get().getRecommendedSlot());
        }
        
        // If no valid recommendation, return empty
        return Optional.empty();
    }
    
    @Override
    public String getStrategyName() {
        return "AI-Powered Scheduling";
    }
}