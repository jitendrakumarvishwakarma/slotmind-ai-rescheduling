package com.slotmind.service.scheduling.impl;

import com.slotmind.ai.service.AIClientService;
import com.slotmind.ai.AIRecommendation;
import com.slotmind.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AISchedulingStrategyTest {

    @Mock
    private AIClientService aiClientService;

    @InjectMocks
    private AISchedulingStrategy aiSchedulingStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindBestSlot_ValidAIRecommendation() {
        // Arrange
        Provider provider = new Provider("PRV-001", "Dr. Smith", Specialty.GENERAL, ProviderStatus.AVAILABLE);
        Slot currentSlot = new Slot("SLT-001", provider, LocalDateTime.now().plusHours(1), 30, SlotStatus.BOOKED);
        Slot recommendedSlot = new Slot("SLT-002", provider, LocalDateTime.now().plusHours(3), 30, SlotStatus.OPEN);
        Appointment appointment = new Appointment("APT-001", "John Doe", "ROUTINE", provider, currentSlot, AppointmentStatus.CONFIRMED);
        
        AIRecommendation aiRecommendation = new AIRecommendation(recommendedSlot, 0.9, "AI recommended slot");
        when(aiClientService.getValidatedRecommendation(any(), anyString())).thenReturn(Optional.of(aiRecommendation));

        // Act
        Optional<Slot> result = aiSchedulingStrategy.findBestSlot(appointment, "PROVIDER_OFFLINE");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("SLT-002", result.get().getId());
        verify(aiClientService, times(1)).getValidatedRecommendation(appointment, "PROVIDER_OFFLINE");
    }

    @Test
    void testFindBestSlot_NoAIRecommendation() {
        // Arrange
        Provider provider = new Provider("PRV-001", "Dr. Smith", Specialty.GENERAL, ProviderStatus.AVAILABLE);
        Slot currentSlot = new Slot("SLT-001", provider, LocalDateTime.now().plusHours(1), 30, SlotStatus.BOOKED);
        Appointment appointment = new Appointment("APT-001", "John Doe", "ROUTINE", provider, currentSlot, AppointmentStatus.CONFIRMED);
        
        when(aiClientService.getValidatedRecommendation(any(), anyString())).thenReturn(Optional.empty());

        // Act
        Optional<Slot> result = aiSchedulingStrategy.findBestSlot(appointment, "PROVIDER_OFFLINE");

        // Assert
        assertFalse(result.isPresent());
        verify(aiClientService, times(1)).getValidatedRecommendation(appointment, "PROVIDER_OFFLINE");
    }

    @Test
    void testGetStrategyName() {
        assertEquals("AI-Powered Scheduling", aiSchedulingStrategy.getStrategyName());
    }
}