package com.slotmind.dto;

import com.slotmind.model.SlotStatus;
import java.time.LocalDateTime;

public class SlotDTO {
    private String id;
    private String providerId;
    private String providerName;
    private LocalDateTime startTime;
    private int durationMinutes;
    private SlotStatus status;
    
    // Constructors
    public SlotDTO() {}
    
    public SlotDTO(String id, String providerId, String providerName, 
                  LocalDateTime startTime, int durationMinutes, SlotStatus status) {
        this.id = id;
        this.providerId = providerId;
        this.providerName = providerName;
        this.startTime = startTime;
        this.durationMinutes = durationMinutes;
        this.status = status;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getProviderId() {
        return providerId;
    }
    
    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
    
    public String getProviderName() {
        return providerName;
    }
    
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public int getDurationMinutes() {
        return durationMinutes;
    }
    
    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
    
    public SlotStatus getStatus() {
        return status;
    }
    
    public void setStatus(SlotStatus status) {
        this.status = status;
    }
}