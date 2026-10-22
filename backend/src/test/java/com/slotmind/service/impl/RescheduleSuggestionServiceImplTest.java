package com.slotmind.service.impl;

import com.slotmind.ai.service.AIClientService;
import com.slotmind.ai.AIRecommendation;
import com.slotmind.ai.AIRecommendationValidator;
import com.slotmind.model.*;
import com.slotmind.repository.AppointmentRepository;
import com.slotmind.repository.RescheduleSuggestionRepository;
import com.slotmind.repository.SlotRepository;
import com.slotmind.service.SchedulingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RescheduleSuggestionServiceImplTest {

    @Mock
    private RescheduleSuggestionRepository rescheduleSuggestionRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private SlotRepository slotRepository;

    @Mock
    private SchedulingService schedulingService;

    @Mock
    private AIClientService aiClientService;

    @Mock
    private AIRecommendationValidator recommendationValidator;

    @Test
    void testGenerateRescheduleSuggestion_AppointmentNotFound() {
        // Arrange
        when(appointmentRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act
        RescheduleSuggestion result = rescheduleSuggestionService.generateRescheduleSuggestion("APT-001", "PROVIDER_OFFLINE");

        // Assert
        assertNull(result);
        verify(appointmentRepository, times(1)).findById("APT-001");
    }

    @Test
    void testGenerateRescheduleSuggestion_ExistingPendingSuggestion() {
        // Arrange
        Provider provider = new Provider("PRV-001", "Dr. Smith", Specialty.GENERAL, ProviderStatus.AVAILABLE);
        Slot currentSlot = new Slot("SLT-001", provider, LocalDateTime.now().plusHours(1), 30, SlotStatus.BOOKED);
        Appointment appointment = new Appointment("APT-001", "John Doe", "ROUTINE", provider, currentSlot, AppointmentStatus.CONFIRMED);
        
    @Test
    void testGenerateRescheduleSuggestion_ValidAIRecommendation() {
        // Arrange
        Provider provider = new Provider("PRV-001", "Dr. Smith", Specialty.GENERAL, ProviderStatus.AVAILABLE);
        Slot currentSlot = new Slot("SLT-001", provider, LocalDateTime.now().plusHours(1), 30, SlotStatus.BOOKED);
        Appointment appointment = new Appointment("APT-001", "John Doe", "ROUTINE", provider, currentSlot, AppointmentStatus.CONFIRMED);
        
        Slot recommendedSlot = new Slot("SLT-002", provider, LocalDateTime.now().plusHours(3), 30, SlotStatus.OPEN);
        AIRecommendation aiRecommendation = new AIRecommendation(recommendedSlot, 0.9, "AI recommended slot");

        when(appointmentRepository.findById(anyString())).thenReturn(Optional.of(appointment));
        when(rescheduleSuggestionRepository.findByAppointmentAndTriggerReasonAndStatus(any(), anyString(), any()))
            .thenReturn(new ArrayList<>());
        when(aiClientService.getValidatedRecommendation(any(), anyString())).thenReturn(Optional.of(aiRecommendation));
        when(recommendationValidator.isValidRecommendation(any())).thenReturn(true);
        when(rescheduleSuggestionRepository.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);

        // Act
        RescheduleSuggestion result = rescheduleSuggestionService.generateRescheduleSuggestion("APT-001", "PROVIDER_OFFLINE");

    @Test
    void testGenerateRescheduleSuggestion_AIFallbackToRuleBased() {
        // Arrange
        Provider provider = new Provider("PRV-001", "Dr. Smith", Specialty.GENERAL, ProviderStatus.AVAILABLE);
        Slot currentSlot = new Slot("SLT-001", provider, LocalDateTime.now().plusHours(1), 30, SlotStatus.BOOKED);
        Appointment appointment = new Appointment("APT-001", "John Doe", "ROUTINE", provider, currentSlot, AppointmentStatus.CONFIRMED);
        
        Slot ruleBasedSlot = new Slot("SLT-003", provider, LocalDateTime.now().plusHours(4), 30, SlotStatus.OPEN);

        when(appointmentRepository.findById(anyString())).thenReturn(Optional.of(appointment));
        when(rescheduleSuggestionRepository.findByAppointmentAndTriggerReasonAndStatus(any(), anyString(), any()))
            .thenReturn(new ArrayList<>());
        when(aiClientService.getValidatedRecommendation(any(), anyString())).thenReturn(Optional.empty());
        when(schedulingService.findBestSlot(any(), anyString())).thenReturn(Optional.of(ruleBasedSlot));
        when(rescheduleSuggestionRepository.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);

        // Act
        RescheduleSuggestion result = rescheduleSuggestionService.generateRescheduleSuggestion("APT-001", "PROVIDER_OFFLINE");

        // Assert
    @Test
    void testAcceptSuggestion_SuccessfulAccept() {
        // Arrange
        Provider provider = new Provider("PRV-001", "Dr. Smith", Specialty.GENERAL, ProviderStatus.AVAILABLE);
        Slot currentSlot = new Slot("SLT-001", provider, LocalDateTime.now().plusHours(1), 30, SlotStatus.BOOKED);
        Slot newSlot = new Slot("SLT-002", provider, LocalDateTime.now().plusHours(3), 30, SlotStatus.OPEN);
        Appointment appointment = new Appointment("APT-001", "John Doe", "ROUTINE", provider, currentSlot, AppointmentStatus.CONFIRMED);
        
        RescheduleSuggestion suggestion = new RescheduleSuggestion(appointment, newSlot, "PROVIDER_OFFLINE", 0.8, "AI recommended");
        suggestion.setId(1L);
        suggestion.setStatus(SuggestionStatus.PENDING);

        when(rescheduleSuggestionRepository.findById(anyLong())).thenReturn(Optional.of(suggestion));
        when(slotRepository.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);
        when(appointmentRepository.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);
        when(rescheduleSuggestionRepository.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);

        // Act
        RescheduleSuggestion result = rescheduleSuggestionService.acceptSuggestion(1L);

    @Test
    void testRejectSuggestion_SuccessfulReject() {
        // Arrange
        Provider provider = new Provider("PRV-001", "Dr. Smith", Specialty.GENERAL, ProviderStatus.AVAILABLE);
        Slot currentSlot = new Slot("SLT-001", provider, LocalDateTime.now().plusHours(1), 30, SlotStatus.BOOKED);
        Slot newSlot = new Slot("SLT-002", provider, LocalDateTime.now().plusHours(3), 30, SlotStatus.OPEN);
        Appointment appointment = new Appointment("APT-001", "John Doe", "ROUTINE", provider, currentSlot, AppointmentStatus.CONFIRMED);
        
        RescheduleSuggestion suggestion = new RescheduleSuggestion(appointment, newSlot, "PROVIDER_OFFLINE", 0.8, "AI recommended");
        suggestion.setId(1L);
        suggestion.setStatus(SuggestionStatus.PENDING);

        when(rescheduleSuggestionRepository.findById(anyLong())).thenReturn(Optional.of(suggestion));
        when(rescheduleSuggestionRepository.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);

        // Act
        RescheduleSuggestion result = rescheduleSuggestionService.rejectSuggestion(1L);

        // Assert
        assertNotNull(result);
        assertEquals(SuggestionStatus.REJECTED, result.getStatus());
        verify(rescheduleSuggestionRepository, times(1)).findById(1L);
        verify(rescheduleSuggestionRepository, times(1)).save(suggestion);
    }
        // Assert
        assertNotNull(result);
        assertEquals(SuggestionStatus.ACCEPTED, result.getStatus());
        assertEquals(SlotStatus.CANCELLED, currentSlot.getStatus());
        assertEquals(SlotStatus.BOOKED, newSlot.getStatus());
        assertEquals(AppointmentStatus.RESCHEDULED, appointment.getStatus());
        assertEquals(newSlot, appointment.getSlot());
        verify(rescheduleSuggestionRepository, times(1)).findById(1L);
        verify(slotRepository, times(2)).save(any());
        verify(appointmentRepository, times(1)).save(appointment);
        verify(rescheduleSuggestionRepository, times(1)).save(suggestion);
    }
        assertNotNull(result);
        assertEquals(appointment, result.getAppointment());
        assertEquals(ruleBasedSlot, result.getNewSlot());
        assertEquals("PROVIDER_OFFLINE", result.getTriggerReason());
        assertEquals(0.6, result.getConfidenceScore()); // Rule-based confidence
        assertEquals("Recommended slot based on rule-based matching (fallback from AI)", result.getExplanation());
        verify(aiClientService, times(1)).getValidatedRecommendation(appointment, "PROVIDER_OFFLINE");
        verify(schedulingService, times(1)).findBestSlot(appointment, "PROVIDER_OFFLINE");
        verify(rescheduleSuggestionRepository, times(1)).save(any());
    }
        // Assert
        assertNotNull(result);
        assertEquals(appointment, result.getAppointment());
        assertEquals(recommendedSlot, result.getNewSlot());
        assertEquals("PROVIDER_OFFLINE", result.getTriggerReason());
        assertEquals(0.9, result.getConfidenceScore());
        assertEquals("AI recommended slot", result.getExplanation());
        verify(aiClientService, times(1)).getValidatedRecommendation(appointment, "PROVIDER_OFFLINE");
        verify(rescheduleSuggestionRepository, times(1)).save(any());
    }
        Slot newSlot = new Slot("SLT-002", provider, LocalDateTime.now().plusHours(2), 30, SlotStatus.OPEN);
        RescheduleSuggestion existingSuggestion = new RescheduleSuggestion(appointment, newSlot, "PROVIDER_OFFLINE", 0.8, "Existing suggestion");

        when(appointmentRepository.findById(anyString())).thenReturn(Optional.of(appointment));
        when(rescheduleSuggestionRepository.findByAppointmentAndTriggerReasonAndStatus(any(), anyString(), any()))
            .thenReturn(java.util.Collections.singletonList(existingSuggestion));

        // Act
        RescheduleSuggestion result = rescheduleSuggestionService.generateRescheduleSuggestion("APT-001", "PROVIDER_OFFLINE");

        // Assert
        assertNotNull(result);
        assertEquals(existingSuggestion, result);
        verify(appointmentRepository, times(1)).findById("APT-001");
        verify(rescheduleSuggestionRepository, times(1))
            .findByAppointmentAndTriggerReasonAndStatus(appointment, "PROVIDER_OFFLINE", SuggestionStatus.PENDING);
    }
    @InjectMocks
    private RescheduleSuggestionServiceImpl rescheduleSuggestionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
}