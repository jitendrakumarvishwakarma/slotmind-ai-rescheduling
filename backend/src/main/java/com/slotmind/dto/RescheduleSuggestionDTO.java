package com.slotmind.dto;

import com.slotmind.model.SuggestionStatus;
import java.time.LocalDateTime;

public class RescheduleSuggestionDTO {
    private Long id;
    private String appointmentId;
    private String patientName;
    private String currentProviderId;
    private String currentProviderName;
    private String newSlotId;
    private String newProviderId;
    private String newProviderName;
    private LocalDateTime newSlotStartTime;
    private int newSlotDurationMinutes;
    private String triggerReason;
    private Double confidenceScore;
    private String explanation;
    private SuggestionStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public RescheduleSuggestionDTO() {}
    
    public RescheduleSuggestionDTO(Long id, String appointmentId, String patientName,
                                  String currentProviderId, String currentProviderName,
                                  String newSlotId, String newProviderId, String newProviderName,
                                  LocalDateTime newSlotStartTime, int newSlotDurationMinutes,
                                  String triggerReason, Double confidenceScore, String explanation,
                                  SuggestionStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.patientName = patientName;
        this.currentProviderId = currentProviderId;
        this.currentProviderName = currentProviderName;
        this.newSlotId = newSlotId;
        this.newProviderId = newProviderId;
        this.newProviderName = newProviderName;
        this.newSlotStartTime = newSlotStartTime;
        this.newSlotDurationMinutes = newSlotDurationMinutes;
        this.triggerReason = triggerReason;
        this.confidenceScore = confidenceScore;
        this.explanation = explanation;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getAppointmentId() {
        return appointmentId;
    }
    
    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }
    
    public String getPatientName() {
        return patientName;
    }
    
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    
    public String getCurrentProviderId() {
        return currentProviderId;
    }
    
    public void setCurrentProviderId(String currentProviderId) {
        this.currentProviderId = currentProviderId;
    }
    
    public String getCurrentProviderName() {
        return currentProviderName;
    }
    
    public void setCurrentProviderName(String currentProviderName) {
        this.currentProviderName = currentProviderName;
    }
    
    public String getNewSlotId() {
        return newSlotId;
    }
    
    public void setNewSlotId(String newSlotId) {
        this.newSlotId = newSlotId;
    }
    
    public String getNewProviderId() {
        return newProviderId;
    }
    
    public void setNewProviderId(String newProviderId) {
        this.newProviderId = newProviderId;
    }
    
    public String getNewProviderName() {
        return newProviderName;
    }
    
    public void setNewProviderName(String newProviderName) {
        this.newProviderName = newProviderName;
    }
    
    public LocalDateTime getNewSlotStartTime() {
        return newSlotStartTime;
    }
    
    public void setNewSlotStartTime(LocalDateTime newSlotStartTime) {
        this.newSlotStartTime = newSlotStartTime;
    }
    
    public int getNewSlotDurationMinutes() {
        return newSlotDurationMinutes;
    }
    
    public void setNewSlotDurationMinutes(int newSlotDurationMinutes) {
        this.newSlotDurationMinutes = newSlotDurationMinutes;
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