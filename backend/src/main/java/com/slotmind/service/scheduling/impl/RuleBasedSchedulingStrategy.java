package com.slotmind.service.scheduling.impl;

import com.slotmind.model.Appointment;
import com.slotmind.model.Provider;
import com.slotmind.model.ProviderStatus;
import com.slotmind.model.Slot;
import com.slotmind.model.SlotStatus;
import com.slotmind.repository.ProviderRepository;
import com.slotmind.repository.SlotRepository;
import com.slotmind.service.scheduling.SchedulingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RuleBasedSchedulingStrategy implements SchedulingStrategy {
    
    @Autowired
    private SlotRepository slotRepository;
    
    @Autowired
    private ProviderRepository providerRepository;
    
    @Override
    public Optional<Slot> findBestSlot(Appointment appointment, String triggerReason) {
        Provider currentProvider = appointment.getProvider();
        
        // Rule 1: Find the nearest open slot for the same provider
        List<Slot> openSlotsForCurrentProvider = slotRepository
            .findOpenSlotsForProviderAfterTime(currentProvider, SlotStatus.OPEN, LocalDateTime.now());
        
        if (!openSlotsForCurrentProvider.isEmpty()) {
            // Return the earliest available slot
            return Optional.of(openSlotsForCurrentProvider.get(0));
        }
        
        // Rule 2: Find an open slot with a matching specialty
        List<Slot> openSlotsBySpecialty = slotRepository
            .findOpenSlotsBySpecialtyAfterTime(currentProvider.getSpecialty().name(), SlotStatus.OPEN, LocalDateTime.now());
        
        if (!openSlotsBySpecialty.isEmpty()) {
            // Filter out slots from the current provider (already checked)
            Optional<Slot> alternativeSlot = openSlotsBySpecialty.stream()
                .filter(slot -> !slot.getProvider().getId().equals(currentProvider.getId()))
                .findFirst();
            
            if (alternativeSlot.isPresent()) {
                return alternativeSlot;
            }
        }
        
        // No suitable slot found
        return Optional.empty();
    }
    
    @Override
    public String getStrategyName() {
        return "Rule-Based Scheduling";
    }
}