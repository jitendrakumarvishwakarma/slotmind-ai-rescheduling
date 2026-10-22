package com.slotmind.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slotmind.model.*;
import com.slotmind.service.RescheduleSuggestionService;
import com.slotmind.service.RescheduleSuggestionMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RescheduleSuggestionController.class)
class RescheduleSuggestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RescheduleSuggestionService rescheduleSuggestionService;

    @MockBean
    private RescheduleSuggestionMapper rescheduleSuggestionMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllSuggestions() throws Exception {
        // Arrange
        Provider provider = new Provider("PRV-001", "Dr. Smith", Specialty.GENERAL, ProviderStatus.AVAILABLE);
        Slot slot = new Slot("SLT-001", provider, LocalDateTime.now().plusHours(1), 30, SlotStatus.BOOKED);
        Appointment appointment = new Appointment("APT-001", "John Doe", "ROUTINE", provider, slot, AppointmentStatus.CONFIRMED);
        Slot newSlot = new Slot("SLT-002", provider, LocalDateTime.now().plusHours(2), 30, SlotStatus.OPEN);
        
        RescheduleSuggestion suggestion = new RescheduleSuggestion(appointment, newSlot, "PROVIDER_OFFLINE", 0.8, "AI recommendation");
        suggestion.setId(1L);
        
        RescheduleSuggestionDTO suggestionDTO = new RescheduleSuggestionDTO(
            1L, "APT-001", "John Doe", "PRV-001", "Dr. Smith",
            "SLT-002", "PRV-001", "Dr. Smith",
            LocalDateTime.now().plusHours(2), 30,
            "PROVIDER_OFFLINE", 0.8, "AI recommendation",
            SuggestionStatus.PENDING, LocalDateTime.now(), LocalDateTime.now()
        );

        when(rescheduleSuggestionService.getAllSuggestions()).thenReturn(Arrays.asList(suggestion));
        when(rescheduleSuggestionMapper.toDTO(any())).thenReturn(suggestionDTO);

        // Act & Assert
        mockMvc.perform(get("/api/reschedule-suggestions"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].patientName").value("John Doe"));
    }

    @Test
    void testGetSuggestionById_Found() throws Exception {
        // Arrange
        Provider provider = new Provider("PRV-001", "Dr. Smith", Specialty.GENERAL, ProviderStatus.AVAILABLE);
        Slot slot = new Slot("SLT-001", provider, LocalDateTime.now().plusHours(1), 30, SlotStatus.BOOKED);
        Appointment appointment = new Appointment("APT-001", "John Doe", "ROUTINE", provider, slot, AppointmentStatus.CONFIRMED);
        Slot newSlot = new Slot("SLT-002", provider, LocalDateTime.now().plusHours(2), 30, SlotStatus.OPEN);
        
        RescheduleSuggestion suggestion = new RescheduleSuggestion(appointment, newSlot, "PROVIDER_OFFLINE", 0.8, "AI recommendation");
        suggestion.setId(1L);
        
        RescheduleSuggestionDTO suggestionDTO = new RescheduleSuggestionDTO(
            1L, "APT-001", "John Doe", "PRV-001", "Dr. Smith",
            "SLT-002", "PRV-001", "Dr. Smith",
            LocalDateTime.now().plusHours(2), 30,
            "PROVIDER_OFFLINE", 0.8, "AI recommendation",
            SuggestionStatus.PENDING, LocalDateTime.now(), LocalDateTime.now()
        );

        when(rescheduleSuggestionService.getSuggestionById(anyLong())).thenReturn(Optional.of(suggestion));
        when(rescheduleSuggestionMapper.toDTO(any())).thenReturn(suggestionDTO);

        // Act & Assert
        mockMvc.perform(get("/api/reschedule-suggestions/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.patientName").value("John Doe"));
    }

    @Test
    void testGetSuggestionById_NotFound() throws Exception {
        // Arrange
        when(rescheduleSuggestionService.getSuggestionById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/reschedule-suggestions/999"))
            .andExpect(status().isNotFound());
    }
}