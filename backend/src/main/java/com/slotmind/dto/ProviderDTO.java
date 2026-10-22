package com.slotmind.dto;

import com.slotmind.model.ProviderStatus;
import com.slotmind.model.Specialty;

public class ProviderDTO {
    private String id;
    private String name;
    private Specialty specialty;
    private ProviderStatus status;
    
    // Constructors
    public ProviderDTO() {}
    
    public ProviderDTO(String id, String name, Specialty specialty, ProviderStatus status) {
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