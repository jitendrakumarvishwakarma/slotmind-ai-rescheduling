package com.slotmind.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    private String id;
    
    private String patientName;
    
    private String appointmentType;
    
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;
    
    @OneToOne
    @JoinColumn(name = "slot_id")
    private Slot slot;
    
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
    
    private LocalDateTime createdAt;
    
    // Constructors
    public Appointment() {}
    
    public Appointment(String id, String patientName, String appointmentType, 
                      Provider provider, Slot slot, AppointmentStatus status) {
        this.id = id;
        this.patientName = patientName;
        this.appointmentType = appointmentType;
        this.provider = provider;
        this.slot = slot;
        this.status = status;
        this.createdAt = LocalDateTime.now();
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
    
    public Provider getProvider() {
        return provider;
    }
    
    public void setProvider(Provider provider) {
        this.provider = provider;
    }
    
    public Slot getSlot() {
        return slot;
    }
    
    public void setSlot(Slot slot) {
        this.slot = slot;
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