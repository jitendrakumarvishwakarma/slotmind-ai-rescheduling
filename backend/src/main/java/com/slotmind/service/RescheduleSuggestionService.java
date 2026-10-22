package com.slotmind.service;

import com.slotmind.model.RescheduleSuggestion;
import com.slotmind.model.SuggestionStatus;
import java.util.List;
import java.util.Optional;

public interface RescheduleSuggestionService {
    List<RescheduleSuggestion> getAllSuggestions();
    Optional<RescheduleSuggestion> getSuggestionById(Long id);
    RescheduleSuggestion createSuggestion(RescheduleSuggestion suggestion);
    RescheduleSuggestion updateSuggestion(Long id, RescheduleSuggestion suggestionDetails);
    void deleteSuggestion(Long id);
    List<RescheduleSuggestion> getSuggestionsByStatus(SuggestionStatus status);
    List<RescheduleSuggestion> getSuggestionsByAppointment(String appointmentId);
    RescheduleSuggestion generateRescheduleSuggestion(String appointmentId, String triggerReason);
    RescheduleSuggestion acceptSuggestion(Long suggestionId);
    RescheduleSuggestion rejectSuggestion(Long suggestionId);
}