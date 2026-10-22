package com.slotmind.ai.impl;

import com.slotmind.ai.AIRecommendation;
import com.slotmind.ai.LLMGateway;
import com.slotmind.model.Appointment;
import com.slotmind.model.Slot;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MockLLMGateway implements LLMGateway {
    
    @Override
    public Optional<AIRecommendation> getReschedulingRecommendation(Appointment appointment, String triggerReason) {
        // This is a mock implementation that simulates AI responses
        // In a real implementation, this would call an actual LLM service
        
        // Simulate occasional AI failures
        if (Math.random() < 0.5) { // 50% chance of failure
            return Optional.empty();
        }
        
        // Simulate finding a recommended slot
        // In a real implementation, this would be determined by the AI
        Slot recommendedSlot = findMockRecommendedSlot(appointment);
        
        if (recommendedSlot == null) {
            return Optional.empty();
        }
        
        // Generate mock confidence score and explanation
        double confidenceScore = 0.7 + Math.random() * 0.3; // Between 0.7 and 1.0
        String explanation = generateMockExplanation(appointment, recommendedSlot, triggerReason);
        
        AIRecommendation recommendation = new AIRecommendation(
            recommendedSlot,
            confidenceScore,
            explanation
        );
        
        return Optional.of(recommendation);
    }
    
    private Slot findMockRecommendedSlot(Appointment appointment) {
        // This is a simplified mock implementation
        // In a real scenario, the AI would analyze various factors
        return null; // For now, just return null to demonstrate fallback
    }
    
    private String generateMockExplanation(Appointment appointment, Slot slot, String triggerReason) {
        StringBuilder explanation = new StringBuilder();
        
        explanation.append("Based on your appointment details and availability, ");
        
        if ("PROVIDER_OFFLINE".equals(triggerReason)) {
            explanation.append("we've found an alternative time slot with ");
            
            if (slot.getProvider().getId().equals(appointment.getProvider().getId())) {
                explanation.append("your original provider");
            } else {
                explanation.append("another provider with the same specialty");
            }
        } else if ("SLOT_CANCELLED".equals(triggerReason)) {
            explanation.append("we've identified a suitable replacement slot");
        }
        
        explanation.append(". This recommendation considers your preferred time windows and provider expertise.");
        
        return explanation.toString();
    }
}