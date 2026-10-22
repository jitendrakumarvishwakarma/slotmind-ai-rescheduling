package com.slotmind.ai.service;

import com.slotmind.ai.AIRecommendation;
import com.slotmind.ai.LLMGateway;
import com.slotmind.ai.AIRecommendationValidator;
import com.slotmind.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AIClientService {
    
    @Autowired
    private LLMGateway llmGateway;
    
    @Autowired
    private AIRecommendationValidator recommendationValidator;
    
    /**
     * Get a validated AI recommendation for rescheduling an appointment
     * @param appointment The appointment that needs rescheduling
     * @param triggerReason The reason for rescheduling
     * @return Validated AI recommendation or empty if AI fails or returns invalid results
     */
    public Optional<AIRecommendation> getValidatedRecommendation(Appointment appointment, String triggerReason) {
        try {
            // Call the LLM gateway to get AI recommendation
            Optional<AIRecommendation> aiRecommendation = llmGateway.getReschedulingRecommendation(appointment, triggerReason);
            
            // If we got a recommendation, validate it
            if (aiRecommendation.isPresent()) {
                AIRecommendation recommendation = aiRecommendation.get();
                
                // Validate the recommendation
                if (recommendationValidator.isValidRecommendation(recommendation)) {
                    // Enhance the recommendation with additional metadata
                    AIRecommendation enhancedRecommendation = recommendationValidator.enhanceRecommendation(recommendation);
                    return Optional.of(enhancedRecommendation);
                }
            }
            
            return Optional.empty();
        } catch (Exception e) {
            // Log the exception (in a real implementation)
            // If AI service fails, return empty to trigger fallback
            return Optional.empty();
        }
    }
}