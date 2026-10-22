package com.slotmind.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reschedule_suggestions")
public class RescheduleSuggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    
    @ManyToOne
    @JoinColumn(name = "new_slot_id")
    private Slot newSlot;
    
    private String triggerReason; // e.g., "PROVIDER_OFFLINE", "SLOT_CANCELLED"
    
    private Double confidenceScore; // AI confidence score (0.0 to 1.0)
    
    private String explanation; // Patient-friendly explanation
    
    @Enumerated(EnumType.STRING)
    private SuggestionStatus status;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    // Constructors
    public RescheduleSuggestion() {}
    
    public RescheduleSuggestion(Appointment appointment, Slot newSlot, String triggerReason, 
                               Double confidenceScore, String explanation) {
        this.appointment = appointment;
        this.newSlot = newSlot;
        this.triggerReason = triggerReason;
        this.confidenceScore = confidenceScore;
        this.explanation = explanation;
        this.status = SuggestionStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Appointment getAppointment() {
        return appointment;
    }
    
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
    
    public Slot getNewSlot() {
        return newSlot;
    }
    
    public void setNewSlot(Slot newSlot) {
        this.newSlot = newSlot;
    }
    
    public String getTriggerReason() {
        return triggerReason;
    }
    
    public void setTriggerReason(String triggerReason) {
        this.triggerReason = triggerReason;
    }
    
    public Double getConfidenceScore() {
        return confidenceScore;
    }
    
    public void setConfidenceScore(Double confidenceScore) {
        this.confidenceScore = confidenceScore;
    }
    
    public String getExplanation() {
        return explanation;
    }
    
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
    
    public SuggestionStatus getStatus() {
        return status;
    }
    
    public void setStatus(SuggestionStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}