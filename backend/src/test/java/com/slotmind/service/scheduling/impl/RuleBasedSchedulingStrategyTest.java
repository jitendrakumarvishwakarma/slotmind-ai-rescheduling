package com.slotmind.service.scheduling.impl;

import com.slotmind.model.*;
import com.slotmind.repository.SlotRepository;
import com.slotmind.service.scheduling.SchedulingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RuleBasedSchedulingStrategyTest {

    @Mock
    private SlotRepository slotRepository;

    @InjectMocks
    private RuleBasedSchedulingStrategy ruleBasedSchedulingStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindBestSlot_SameProviderAvailable() {
        // Arrange
        Provider provider = new Provider("PRV-001", "Dr. Smith", Specialty.GENERAL, ProviderStatus.AVAILABLE);
        Slot currentSlot = new Slot("SLT-001", provider, LocalDateTime.now().plusHours(1), 30, SlotStatus.BOOKED);
        Appointment appointment = new Appointment("APT-001", "John Doe", "ROUTINE", provider, currentSlot, AppointmentStatus.CONFIRMED);

        Slot openSlot = new Slot("SLT-002", provider, LocalDateTime.now().plusHours(2), 30, SlotStatus.OPEN);
        List<Slot> openSlots = new ArrayList<>();
        openSlots.add(openSlot);

        when(slotRepository.findOpenSlotsForProviderAfterTime(any(), any(), any())).thenReturn(openSlots);

        // Act
        Optional<Slot> result = ruleBasedSchedulingStrategy.findBestSlot(appointment, "PROVIDER_OFFLINE");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("SLT-002", result.get().getId());
        verify(slotRepository, times(1)).findOpenSlotsForProviderAfterTime(provider, SlotStatus.OPEN, LocalDateTime.now());
    }

    @Test
    void testFindBestSlot_NoSameProvider_SpecialtyMatch() {
        // Arrange
        Provider currentProvider = new Provider("PRV-001", "Dr. Smith", Specialty.GENERAL, ProviderStatus.OFFLINE);
        Provider alternateProvider = new Provider("PRV-002", "Dr. Jones", Specialty.GENERAL, ProviderStatus.AVAILABLE);
        
        Slot currentSlot = new Slot("SLT-001", currentProvider, LocalDateTime.now().plusHours(1), 30, SlotStatus.BOOKED);
        Appointment appointment = new Appointment("APT-001", "John Doe", "ROUTINE", currentProvider, currentSlot, AppointmentStatus.CONFIRMED);

        Slot openSlot = new Slot("SLT-002", alternateProvider, LocalDateTime.now().plusHours(2), 30, SlotStatus.OPEN);
        List<Slot> openSlots = new ArrayList<>();
        openSlots.add(openSlot);

        when(slotRepository.findOpenSlotsForProviderAfterTime(any(), any(), any())).thenReturn(new ArrayList<>());
        when(slotRepository.findOpenSlotsBySpecialtyAfterTime(anyString(), any(), any())).thenReturn(openSlots);

        // Act
        Optional<Slot> result = ruleBasedSchedulingStrategy.findBestSlot(appointment, "PROVIDER_OFFLINE");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("SLT-002", result.get().getId());
        verify(slotRepository, times(1)).findOpenSlotsForProviderAfterTime(currentProvider, SlotStatus.OPEN, LocalDateTime.now());
        verify(slotRepository, times(1)).findOpenSlotsBySpecialtyAfterTime("GENERAL", SlotStatus.OPEN, LocalDateTime.now());
    }

    @Test
    void testFindBestSlot_NoAvailableSlots() {
        // Arrange
        Provider provider = new Provider("PRV-001", "Dr. Smith", Specialty.GENERAL, ProviderStatus.AVAILABLE);
        Slot currentSlot = new Slot("SLT-001", provider, LocalDateTime.now().plusHours(1), 30, SlotStatus.BOOKED);
        Appointment appointment = new Appointment("APT-001", "John Doe", "ROUTINE", provider, currentSlot, AppointmentStatus.CONFIRMED);

        when(slotRepository.findOpenSlotsForProviderAfterTime(any(), any(), any())).thenReturn(new ArrayList<>());
        when(slotRepository.findOpenSlotsBySpecialtyAfterTime(anyString(), any(), any())).thenReturn(new ArrayList<>());

        // Act
        Optional<Slot> result = ruleBasedSchedulingStrategy.findBestSlot(appointment, "PROVIDER_OFFLINE");

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testGetStrategyName() {
        assertEquals("Rule-Based Scheduling", ruleBasedSchedulingStrategy.getStrategyName());
    }
}