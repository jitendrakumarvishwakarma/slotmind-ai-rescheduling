package com.slotmind.service.impl;

import com.slotmind.model.Appointment;
import com.slotmind.model.AppointmentStatus;
import com.slotmind.model.Provider;
import com.slotmind.model.ProviderStatus;
import com.slotmind.repository.AppointmentRepository;
import com.slotmind.repository.ProviderRepository;
import com.slotmind.service.ProviderService;
import com.slotmind.service.RescheduleSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderServiceImpl implements ProviderService {
    
    @Autowired
    private ProviderRepository providerRepository;
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private RescheduleSuggestionService rescheduleSuggestionService;
    
    @Override
    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }
    
    @Override
    public Optional<Provider> getProviderById(String id) {
        return providerRepository.findById(id);
    }
    
    @Override
    public Provider createProvider(Provider provider) {
        return providerRepository.save(provider);
    }
    
    @Override
    public Provider updateProvider(String id, Provider providerDetails) {
        Optional<Provider> providerOptional = providerRepository.findById(id);
        if (providerOptional.isPresent()) {
            Provider provider = providerOptional.get();
            ProviderStatus oldStatus = provider.getStatus();
            ProviderStatus newStatus = providerDetails.getStatus();
            
            provider.setName(providerDetails.getName());
            provider.setSpecialty(providerDetails.getSpecialty());
            provider.setStatus(providerDetails.getStatus());
            
            Provider updatedProvider = providerRepository.save(provider);
            
            // If provider status changed to OFFLINE, trigger rescheduling for confirmed appointments
            if (oldStatus != ProviderStatus.OFFLINE && newStatus == ProviderStatus.OFFLINE) {
                triggerReschedulingForProvider(provider);
            }
            
            return updatedProvider;
        }
        return null;
    }
    
    @Override
    public void deleteProvider(String id) {
        providerRepository.deleteById(id);
    }
    
    @Override
    public List<Provider> getProvidersByStatus(ProviderStatus status) {
        return providerRepository.findByStatus(status);
    }
    
    private void triggerReschedulingForProvider(Provider provider) {
        // Find all confirmed appointments for this provider
        List<Appointment> confirmedAppointments = appointmentRepository
            .findByProviderAndStatus(provider, AppointmentStatus.CONFIRMED);
        
        // Generate reschedule suggestions for each appointment
        for (Appointment appointment : confirmedAppointments) {
            rescheduleSuggestionService.generateRescheduleSuggestion(
                appointment.getId(), "PROVIDER_OFFLINE");
        }
    }
}