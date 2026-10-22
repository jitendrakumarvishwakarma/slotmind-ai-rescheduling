package com.slotmind.service;

import com.slotmind.ai.AIRecommendation;
import com.slotmind.ai.service.AIClientService;
import com.slotmind.model.Appointment;
import com.slotmind.model.Slot;
import com.slotmind.service.scheduling.SchedulingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SchedulingService {
    
    @Autowired
    @Qualifier("ruleBasedSchedulingStrategy")
    private SchedulingStrategy ruleBasedStrategy;
    
    @Autowired
    @Qualifier("aiSchedulingStrategy")
    private SchedulingStrategy aiStrategy;
    
    @Autowired
    private AIClientService aiClientService;
    
    public Optional<Slot> findBestSlot(Appointment appointment, String triggerReason) {
        // Try AI strategy first
        Optional<Slot> aiSlot = aiStrategy.findBestSlot(appointment, triggerReason);
        
        // If AI strategy succeeds, return the result
        if (aiSlot.isPresent()) {
            return aiSlot;
        }
        
        // Fallback to rule-based strategy
        return ruleBasedStrategy.findBestSlot(appointment, triggerReason);
    }
    
    public String getActiveStrategyName() {
        return "Hybrid (AI with Rule-Based Fallback)";
    }
}