package com.slotmind.service;

import com.slotmind.dto.RescheduleSuggestionDTO;
import com.slotmind.model.RescheduleSuggestion;
import org.springframework.stereotype.Service;

@Service
public class RescheduleSuggestionMapper {
    
    public RescheduleSuggestionDTO toDTO(RescheduleSuggestion suggestion) {
        if (suggestion == null) {
            return null;
        }
        
        return new RescheduleSuggestionDTO(
            suggestion.getId(),
            suggestion.getAppointment().getId(),
            suggestion.getAppointment().getPatientName(),
            suggestion.getAppointment().getProvider().getId(),
            suggestion.getAppointment().getProvider().getName(),
            suggestion.getNewSlot().getId(),
            suggestion.getNewSlot().getProvider().getId(),
            suggestion.getNewSlot().getProvider().getName(),
            suggestion.getNewSlot().getStartTime(),
            suggestion.getNewSlot().getDurationMinutes(),
            suggestion.getTriggerReason(),
            suggestion.getConfidenceScore(),
            suggestion.getExplanation(),
            suggestion.getStatus(),
            suggestion.getCreatedAt(),
            suggestion.getUpdatedAt()
        );
    }
}