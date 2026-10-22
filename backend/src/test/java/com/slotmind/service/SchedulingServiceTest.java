package com.slotmind.service;

import com.slotmind.ai.service.AIClientService;
import com.slotmind.model.*;
import com.slotmind.service.scheduling.SchedulingStrategy;
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

class SchedulingServiceTest {

    @Mock
    private SchedulingStrategy ruleBasedStrategy;

    @Mock
    private SchedulingStrategy aiStrategy;

    @Mock
    private AIClientService aiClientService;

    @InjectMocks
    private SchedulingService schedulingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindBestSlot_AISuccess() {
        // Arrange
        Provider provider = new Provider("PRV-001", "Dr. Smith", Specialty.GENERAL, ProviderStatus.AVAILABLE);
        Slot currentSlot = new Slot("SLT-001", provider, LocalDateTime.now().plusHours(1), 30, SlotStatus.BOOKED);
        Slot aiSlot = new Slot("SLT-002", provider, LocalDateTime.now().plusHours(2), 30, SlotStatus.OPEN);
        Appointment appointment = new Appointment("APT-001", "John Doe", "ROUTINE", provider, currentSlot, AppointmentStatus.CONFIRMED);

        when(aiStrategy.findBestSlot(any(), anyString())).thenReturn(Optional.of(aiSlot));

        // Act
        Optional<Slot> result = schedulingService.findBestSlot(appointment, "PROVIDER_OFFLINE");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("SLT-002", result.get().getId());
        verify(aiStrategy, times(1)).findBestSlot(appointment, "PROVIDER_OFFLINE");
        verify(ruleBasedStrategy, never()).findBestSlot(any(), anyString());
    }

    @Test
    void testFindBestSlot_AIFailure_RuleBasedSuccess() {
        // Arrange
        Provider provider = new Provider("PRV-001", "Dr. Smith", Specialty.GENERAL, ProviderStatus.AVAILABLE);
        Slot currentSlot = new Slot("SLT-001", provider, LocalDateTime.now().plusHours(1), 30, SlotStatus.BOOKED);
        Slot ruleBasedSlot = new Slot("SLT-003", provider, LocalDateTime.now().plusHours(3), 30, SlotStatus.OPEN);
        Appointment appointment = new Appointment("APT-001", "John Doe", "ROUTINE", provider, currentSlot, AppointmentStatus.CONFIRMED);

        when(aiStrategy.findBestSlot(any(), anyString())).thenReturn(Optional.empty());
        when(ruleBasedStrategy.findBestSlot(any(), anyString())).thenReturn(Optional.of(ruleBasedSlot));

        // Act
        Optional<Slot> result = schedulingService.findBestSlot(appointment, "PROVIDER_OFFLINE");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("SLT-003", result.get().getId());
        verify(aiStrategy, times(1)).findBestSlot(appointment, "PROVIDER_OFFLINE");
        verify(ruleBasedStrategy, times(1)).findBestSlot(appointment, "PROVIDER_OFFLINE");
    }

    @Test
    void testFindBestSlot_BothStrategiesFail() {
        // Arrange
        Provider provider = new Provider("PRV-001", "Dr. Smith", Specialty.GENERAL, ProviderStatus.AVAILABLE);
        Slot currentSlot = new Slot("SLT-001", provider, LocalDateTime.now().plusHours(1), 30, SlotStatus.BOOKED);
        Appointment appointment = new Appointment("APT-001", "John Doe", "ROUTINE", provider, currentSlot, AppointmentStatus.CONFIRMED);

        when(aiStrategy.findBestSlot(any(), anyString())).thenReturn(Optional.empty());
        when(ruleBasedStrategy.findBestSlot(any(), anyString())).thenReturn(Optional.empty());

        // Act
        Optional<Slot> result = schedulingService.findBestSlot(appointment, "PROVIDER_OFFLINE");

        // Assert
        assertFalse(result.isPresent());
        verify(aiStrategy, times(1)).findBestSlot(appointment, "PROVIDER_OFFLINE");
        verify(ruleBasedStrategy, times(1)).findBestSlot(appointment, "PROVIDER_OFFLINE");
    }

    @Test
    void testGetActiveStrategyName() {
        assertEquals("Hybrid (AI with Rule-Based Fallback)", schedulingService.getActiveStrategyName());
    }
}