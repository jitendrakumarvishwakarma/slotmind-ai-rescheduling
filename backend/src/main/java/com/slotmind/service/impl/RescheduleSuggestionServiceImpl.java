package com.slotmind.service.impl;

import com.slotmind.ai.AIRecommendation;
import com.slotmind.ai.AIRecommendationValidator;
import com.slotmind.ai.LLMGateway;
import com.slotmind.model.*;
import com.slotmind.repository.AppointmentRepository;
import com.slotmind.repository.RescheduleSuggestionRepository;
import com.slotmind.repository.SlotRepository;
import com.slotmind.service.SchedulingService;
import com.slotmind.service.RescheduleSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RescheduleSuggestionServiceImpl implements RescheduleSuggestionService {
    
    @Autowired
    private RescheduleSuggestionRepository rescheduleSuggestionRepository;
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private SlotRepository slotRepository;
    
    @Autowired
    private SchedulingService schedulingService;
    
    @Autowired
    private LLMGateway llmGateway;
    
    @Autowired
    private AIRecommendationValidator recommendationValidator;
    
    @Override
    public List<RescheduleSuggestion> getAllSuggestions() {
        return rescheduleSuggestionRepository.findAll();
    }
    
    @Override
    public Optional<RescheduleSuggestion> getSuggestionById(Long id) {
        return rescheduleSuggestionRepository.findById(id);
    }
    
    @Override
    public RescheduleSuggestion createSuggestion(RescheduleSuggestion suggestion) {
        return rescheduleSuggestionRepository.save(suggestion);
    }
    
    @Override
    public RescheduleSuggestion updateSuggestion(Long id, RescheduleSuggestion suggestionDetails) {
        Optional<RescheduleSuggestion> suggestionOptional = rescheduleSuggestionRepository.findById(id);
        if (suggestionOptional.isPresent()) {
            RescheduleSuggestion suggestion = suggestionOptional.get();
            suggestion.setAppointment(suggestionDetails.getAppointment());
            suggestion.setNewSlot(suggestionDetails.getNewSlot());
            suggestion.setTriggerReason(suggestionDetails.getTriggerReason());
            suggestion.setConfidenceScore(suggestionDetails.getConfidenceScore());
            suggestion.setExplanation(suggestionDetails.getExplanation());
            suggestion.setStatus(suggestionDetails.getStatus());
            return rescheduleSuggestionRepository.save(suggestion);
        }
        return null;
    }
    
    
    @Override
    public RescheduleSuggestion generateRescheduleSuggestion(String appointmentId, String triggerReason) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointmentId);
        if (!appointmentOptional.isPresent()) {
            return null;
        }
        
        Appointment appointment = appointmentOptional.get();
        
        // Check if there's already a pending suggestion for this appointment and trigger reason
        List<RescheduleSuggestion> existingSuggestions = rescheduleSuggestionRepository
            .findByAppointmentAndTriggerReasonAndStatus(appointment, triggerReason, SuggestionStatus.PENDING);
        
        if (!existingSuggestions.isEmpty()) {
            // Return existing pending suggestion
            return existingSuggestions.get(0);
        }
        
        // Try to get AI recommendation first
        Optional<AIRecommendation> aiRecommendation = llmGateway.getReschedulingRecommendation(appointment, triggerReason);
        Slot bestSlot = null;
        Double confidenceScore = 0.8;
        String explanation = "Recommended slot based on availability and provider specialty match";
        
        // Check if AI recommendation is valid
        if (aiRecommendation.isPresent() && recommendationValidator.isValidRecommendation(aiRecommendation.get())) {
            bestSlot = aiRecommendation.get().getRecommendedSlot();
            confidenceScore = aiRecommendation.get().getConfidenceScore();
            explanation = aiRecommendation.get().getExplanation();
        } else {
            // Fallback to rule-based scheduling
            Optional<Slot> ruleBasedSlot = schedulingService.findBestSlot(appointment, triggerReason);
            if (ruleBasedSlot.isPresent()) {
                bestSlot = ruleBasedSlot.get();
                confidenceScore = 0.6; // Lower confidence for rule-based suggestions
                explanation = "Recommended slot based on rule-based matching (fallback from AI)";
            }
        }
        
        // If no suitable slot found, return null
        if (bestSlot == null) {
            return null;
        }
        
    
    @Override
    public RescheduleSuggestion acceptSuggestion(Long suggestionId) {
        Optional<RescheduleSuggestion> suggestionOptional = rescheduleSuggestionRepository.findById(suggestionId);
        if (!suggestionOptional.isPresent()) {
            return null;
        }
        
        RescheduleSuggestion suggestion = suggestionOptional.get();
        
        // Check if suggestion is still pending
        if (suggestion.getStatus() != SuggestionStatus.PENDING) {
            return suggestion; // Already processed
        }
        
        // Get the appointment and new slot
        Appointment appointment = suggestion.getAppointment();
        Slot newSlot = suggestion.getNewSlot();
        
        // Check if the new slot is still available
        if (newSlot.getStatus() != SlotStatus.OPEN) {
            // Slot is no longer available, mark suggestion as rejected
            suggestion.setStatus(SuggestionStatus.REJECTED);
            return rescheduleSuggestionRepository.save(suggestion);
        }
        
        // Transactional updates:
        // 1. Mark current slot as cancelled
        Slot currentSlot = appointment.getSlot();
        currentSlot.setStatus(SlotStatus.CANCELLED);
        slotRepository.save(currentSlot);
        
        // 2. Mark new slot as booked
        newSlot.setStatus(SlotStatus.BOOKED);
        slotRepository.save(newSlot);
        
        // 3. Update appointment with new slot
        appointment.setSlot(newSlot);
        appointment.setStatus(AppointmentStatus.RESCHEDULED);
        appointmentRepository.save(appointment);
        
        // 4. Mark suggestion as accepted
        suggestion.setStatus(SuggestionStatus.ACCEPTED);
        return rescheduleSuggestionRepository.save(suggestion);
    }
    
    @Override
    public RescheduleSuggestion rejectSuggestion(Long suggestionId) {
        Optional<RescheduleSuggestion> suggestionOptional = rescheduleSuggestionRepository.findById(suggestionId);
        if (!suggestionOptional.isPresent()) {
            return null;
        }
        
        RescheduleSuggestion suggestion = suggestionOptional.get();
        suggestion.setStatus(SuggestionStatus.REJECTED);
        return rescheduleSuggestionRepository.save(suggestion);
    }
}
        // Create reschedule suggestion with AI-enhanced details
        RescheduleSuggestion suggestion = new RescheduleSuggestion(
            appointment,
            bestSlot,
            triggerReason,
            confidenceScore,
            explanation
        );
        
        return rescheduleSuggestionRepository.save(suggestion);
    }
    @Override
    public void deleteSuggestion(Long id) {
        rescheduleSuggestionRepository.deleteById(id);
    }
    
    @Override
    public List<RescheduleSuggestion> getSuggestionsByStatus(SuggestionStatus status) {
        return rescheduleSuggestionRepository.findByStatus(status);
    }
    
    @Override
    public List<RescheduleSuggestion> getSuggestionsByAppointment(String appointmentId) {
        // Would need to fetch appointment first
        return rescheduleSuggestionRepository.findAll();
    }