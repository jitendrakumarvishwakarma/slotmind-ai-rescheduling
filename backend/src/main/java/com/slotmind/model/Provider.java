package com.slotmind.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "providers")
public class Provider {
    @Id
    private String id;
    
    private String name;
    
    @Enumerated(EnumType.STRING)
    private Specialty specialty;
    
    @Enumerated(EnumType.STRING)
    private ProviderStatus status;
    
    // Constructors
    public Provider() {}
    
    public Provider(String id, String name, Specialty specialty, ProviderStatus status) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.status = status;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Specialty getSpecialty() {
        return specialty;
    }
    
    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }
    
    public ProviderStatus getStatus() {
        return status;
    }
    
    public void setStatus(ProviderStatus status) {
        this.status = status;
    }
}