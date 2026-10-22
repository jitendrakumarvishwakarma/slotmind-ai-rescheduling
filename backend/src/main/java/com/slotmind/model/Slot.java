package com.slotmind.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "slots")
public class Slot {
    @Id
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;
    
    private LocalDateTime startTime;
    
    private int durationMinutes;
    
    @Enumerated(EnumType.STRING)
    private SlotStatus status;
    
    // Constructors
    public Slot() {}
    
    public Slot(String id, Provider provider, LocalDateTime startTime, int durationMinutes, SlotStatus status) {
        this.id = id;
        this.provider = provider;
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
    
    public Provider getProvider() {
        return provider;
    }
    
    public void setProvider(Provider provider) {
        this.provider = provider;
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