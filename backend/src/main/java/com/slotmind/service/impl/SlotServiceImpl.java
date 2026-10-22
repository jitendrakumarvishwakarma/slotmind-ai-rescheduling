package com.slotmind.service.impl;

import com.slotmind.model.Appointment;
import com.slotmind.model.AppointmentStatus;
import com.slotmind.model.Provider;
import com.slotmind.model.Slot;
import com.slotmind.model.SlotStatus;
import com.slotmind.repository.AppointmentRepository;
import com.slotmind.repository.ProviderRepository;
import com.slotmind.repository.SlotRepository;
import com.slotmind.service.RescheduleSuggestionService;
import com.slotmind.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SlotServiceImpl implements SlotService {
    
    @Autowired
    private SlotRepository slotRepository;
    
    @Autowired
    private ProviderRepository providerRepository;
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private RescheduleSuggestionService rescheduleSuggestionService;
    
    @Override
    public List<Slot> getAllSlots() {
        return slotRepository.findAll();
    }
    
    @Override
    public Optional<Slot> getSlotById(String id) {
        return slotRepository.findById(id);
    }
    
    @Override
    public Slot createSlot(Slot slot) {
        return slotRepository.save(slot);
    }
    
    @Override
    public Slot updateSlot(String id, Slot slotDetails) {
        Optional<Slot> slotOptional = slotRepository.findById(id);
        if (slotOptional.isPresent()) {
            Slot slot = slotOptional.get();
            SlotStatus oldStatus = slot.getStatus();
            SlotStatus newStatus = slotDetails.getStatus();
            
            slot.setProvider(slotDetails.getProvider());
            slot.setStartTime(slotDetails.getStartTime());
            slot.setDurationMinutes(slotDetails.getDurationMinutes());
            slot.setStatus(slotDetails.getStatus());
            
            Slot updatedSlot = slotRepository.save(slot);
            
            // If slot status changed to CANCELLED, trigger rescheduling for confirmed appointment
            if (oldStatus != SlotStatus.CANCELLED && newStatus == SlotStatus.CANCELLED) {
                triggerReschedulingForSlot(slot);
            }
            
            return updatedSlot;
        }
        return null;
    }
    
    @Override
    public void deleteSlot(String id) {
        slotRepository.deleteById(id);
    }
    
    @Override
    public List<Slot> getSlotsByStatus(SlotStatus status) {
        return slotRepository.findByStatus(status);
    }
    
    @Override
    public List<Slot> getOpenSlotsForProvider(String providerId) {
        Optional<Provider> providerOptional = providerRepository.findById(providerId);
        if (providerOptional.isPresent()) {
            Provider provider = providerOptional.get();
            return slotRepository.findOpenSlotsForProviderAfterTime(provider, SlotStatus.OPEN, LocalDateTime.now());
        }
        return List.of();
    }
    
    @Override
    public List<Slot> getOpenSlotsBySpecialty(String specialty) {
        return slotRepository.findOpenSlotsBySpecialtyAfterTime(specialty, SlotStatus.OPEN, LocalDateTime.now());
    }
    
    private void triggerReschedulingForSlot(Slot slot) {
        // Find the confirmed appointment for this slot
        List<Appointment> confirmedAppointments = appointmentRepository
            .findBySlotAndStatus(slot, AppointmentStatus.CONFIRMED);
        
        // Generate reschedule suggestion for the appointment
        for (Appointment appointment : confirmedAppointments) {
            rescheduleSuggestionService.generateRescheduleSuggestion(
                appointment.getId(), "SLOT_CANCELLED");
        }
    }
}