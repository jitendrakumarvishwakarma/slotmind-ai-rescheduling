package com.slotmind.dto;

import com.slotmind.model.AppointmentStatus;
import java.time.LocalDateTime;

public class AppointmentDTO {
    private String id;
    private String patientName;
    private String appointmentType;
    private String providerId;
    private String providerName;
    private String slotId;
    private LocalDateTime slotStartTime;
    private int slotDurationMinutes;
    private AppointmentStatus status;
    private LocalDateTime createdAt;
    
    // Constructors
    public AppointmentDTO() {}
    
    public AppointmentDTO(String id, String patientName, String appointmentType,
                         String providerId, String providerName, String slotId,
                         LocalDateTime slotStartTime, int slotDurationMinutes,
                         AppointmentStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.patientName = patientName;
        this.appointmentType = appointmentType;
        this.providerId = providerId;
        this.providerName = providerName;
        this.slotId = slotId;
        this.slotStartTime = slotStartTime;
        this.slotDurationMinutes = slotDurationMinutes;
        this.status = status;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getPatientName() {
        return patientName;
    }
    
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    
    public String getAppointmentType() {
        return appointmentType;
    }
    
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
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
    
    public String getSlotId() {
        return slotId;
    }
    
    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }
    
    public LocalDateTime getSlotStartTime() {
        return slotStartTime;
    }
    
    public void setSlotStartTime(LocalDateTime slotStartTime) {
        this.slotStartTime = slotStartTime;
    }
    
    public int getSlotDurationMinutes() {
        return slotDurationMinutes;
    }
    
    public void setSlotDurationMinutes(int slotDurationMinutes) {
        this.slotDurationMinutes = slotDurationMinutes;
    }
    
    public AppointmentStatus getStatus() {
        return status;
    }
    
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}