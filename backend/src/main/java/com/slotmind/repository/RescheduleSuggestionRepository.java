package com.slotmind.repository;

import com.slotmind.model.RescheduleSuggestion;
import com.slotmind.model.SuggestionStatus;
import com.slotmind.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RescheduleSuggestionRepository extends JpaRepository<RescheduleSuggestion, Long> {
    List<RescheduleSuggestion> findByStatus(SuggestionStatus status);
    List<RescheduleSuggestion> findByAppointmentAndStatus(Appointment appointment, SuggestionStatus status);
    List<RescheduleSuggestion> findByAppointmentAndTriggerReasonAndStatus(
        Appointment appointment, String triggerReason, SuggestionStatus status);
}