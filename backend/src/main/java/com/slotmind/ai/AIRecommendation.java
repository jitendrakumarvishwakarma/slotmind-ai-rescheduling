package com.slotmind.ai;

import com.slotmind.model.Slot;

public class AIRecommendation {
    private Slot recommendedSlot;
    private double confidenceScore; // Between 0.0 and 1.0
    private String explanation; // Patient-friendly explanation
    private String alternateProviderId; // Optional alternate provider recommendation
    
    public AIRecommendation(Slot recommendedSlot, double confidenceScore, String explanation) {
        this.recommendedSlot = recommendedSlot;
        this.confidenceScore = confidenceScore;
        this.explanation = explanation;
    }
    
    public AIRecommendation(Slot recommendedSlot, double confidenceScore, String explanation, String alternateProviderId) {
        this(recommendedSlot, confidenceScore, explanation);
        this.alternateProviderId = alternateProviderId;
    }
    
    // Getters and setters
    public Slot getRecommendedSlot() {
        return recommendedSlot;
    }
    
    public void setRecommendedSlot(Slot recommendedSlot) {
        this.recommendedSlot = recommendedSlot;
    }
    
    public double getConfidenceScore() {
        return confidenceScore;
    }
    
    public void setConfidenceScore(double confidenceScore) {
        this.confidenceScore = confidenceScore;
    }
    
    public String getExplanation() {
        return explanation;
    }
    
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
    
    public String getAlternateProviderId() {
        return alternateProviderId;
    }
    
    public void setAlternateProviderId(String alternateProviderId) {
        this.alternateProviderId = alternateProviderId;
    }
}