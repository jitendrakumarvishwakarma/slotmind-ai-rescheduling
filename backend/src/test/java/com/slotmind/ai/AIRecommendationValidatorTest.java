package com.slotmind.ai;

import com.slotmind.model.Slot;
import com.slotmind.model.SlotStatus;
import com.slotmind.repository.SlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AIRecommendationValidatorTest {

    @Mock
    private SlotRepository slotRepository;

    @InjectMocks
    @Test
    void testIsValidRecommendation_NullRecommendation() {
        // Act
        boolean result = aiRecommendationValidator.isValidRecommendation(null);

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsValidRecommendation_NullSlot() {
        // Arrange
        AIRecommendation recommendation = new AIRecommendation(null, 0.8, "Valid explanation");

        // Act
        boolean result = aiRecommendationValidator.isValidRecommendation(recommendation);

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsValidRecommendation_NullSlotId() {
        // Arrange
        Slot slot = new Slot();
    @Test
    void testIsValidRecommendation_SlotNotOpen() {
        // Arrange
        Slot slot = new Slot();
        slot.setId("SLT-001");
        slot.setStatus(SlotStatus.BOOKED); // Not OPEN
        AIRecommendation recommendation = new AIRecommendation(slot, 0.8, "Valid explanation");
        
        when(slotRepository.existsById(anyString())).thenReturn(true);
        when(slotRepository.findById(anyString())).thenReturn(Optional.of(slot));

        // Act
        boolean result = aiRecommendationValidator.isValidRecommendation(recommendation);

        // Assert
        assertFalse(result);
        verify(slotRepository, times(1)).existsById("SLT-001");
        verify(slotRepository, times(1)).findById("SLT-001");
    }

    @Test
    void testIsValidRecommendation_SlotInPast() {
        // Arrange
        Slot slot = new Slot();
        slot.setId("SLT-001");
    @Test
    void testIsValidRecommendation_InvalidConfidenceScore_TooHigh() {
        // Arrange
        Slot slot = new Slot();
        slot.setId("SLT-001");
        slot.setStatus(SlotStatus.OPEN);
        slot.setStartTime(LocalDateTime.now().plusHours(1));
        AIRecommendation recommendation = new AIRecommendation(slot, 1.5, "Valid explanation");
        
        when(slotRepository.existsById(anyString())).thenReturn(true);
        when(slotRepository.findById(anyString())).thenReturn(Optional.of(slot));

        // Act
        boolean result = aiRecommendationValidator.isValidRecommendation(recommendation);

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsValidRecommendation_EmptyExplanation() {
        // Arrange
        Slot slot = new Slot();
        slot.setId("SLT-001");
        slot.setStatus(SlotStatus.OPEN);
        slot.setStartTime(LocalDateTime.now().plusHours(1));
        AIRecommendation recommendation = new AIRecommendation(slot, 0.8, "");
        
        when(slotRepository.existsById(anyString())).thenReturn(true);
        when(slotRepository.findById(anyString())).thenReturn(Optional.of(slot));

        // Act
        boolean result = aiRecommendationValidator.isValidRecommendation(recommendation);

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsValidRecommendation_ValidRecommendation() {
        // Arrange
        Slot slot = new Slot();
        slot.setId("SLT-001");
        slot.setStatus(SlotStatus.OPEN);
        slot.setStartTime(LocalDateTime.now().plusHours(1));
        AIRecommendation recommendation = new AIRecommendation(slot, 0.8, "Valid explanation");
        
        when(slotRepository.existsById(anyString())).thenReturn(true);
        when(slotRepository.findById(anyString())).thenReturn(Optional.of(slot));

        // Act
        boolean result = aiRecommendationValidator.isValidRecommendation(recommendation);

        // Assert
        assertTrue(result);
    }

    @Test
    void testEnhanceRecommendation_NullRecommendation() {
        // Act
        AIRecommendation result = aiRecommendationValidator.enhanceRecommendation(null);

        // Assert
        assertNull(result);
    }
        slot.setStatus(SlotStatus.OPEN);
        slot.setStartTime(LocalDateTime.now().minusHours(1)); // In the past
        AIRecommendation recommendation = new AIRecommendation(slot, 0.8, "Valid explanation");
        
        when(slotRepository.existsById(anyString())).thenReturn(true);
        when(slotRepository.findById(anyString())).thenReturn(Optional.of(slot));

        // Act
        boolean result = aiRecommendationValidator.isValidRecommendation(recommendation);

        // Assert
        assertFalse(result);
        verify(slotRepository, times(1)).existsById("SLT-001");
        verify(slotRepository, times(1)).findById("SLT-001");
    }

    @Test
    void testIsValidRecommendation_InvalidConfidenceScore_TooLow() {
        // Arrange
        Slot slot = new Slot();
        slot.setId("SLT-001");
        slot.setStatus(SlotStatus.OPEN);
        slot.setStartTime(LocalDateTime.now().plusHours(1));
        AIRecommendation recommendation = new AIRecommendation(slot, -0.1, "Valid explanation");
        
        when(slotRepository.existsById(anyString())).thenReturn(true);
        when(slotRepository.findById(anyString())).thenReturn(Optional.of(slot));

        // Act
        boolean result = aiRecommendationValidator.isValidRecommendation(recommendation);

        // Assert
        assertFalse(result);
    }
        // Don't set ID to keep it null
        AIRecommendation recommendation = new AIRecommendation(slot, 0.8, "Valid explanation");

        // Act
        boolean result = aiRecommendationValidator.isValidRecommendation(recommendation);

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsValidRecommendation_SlotNotInDatabase() {
        // Arrange
        Slot slot = new Slot();
        slot.setId("SLT-001");
        AIRecommendation recommendation = new AIRecommendation(slot, 0.8, "Valid explanation");
        
        when(slotRepository.existsById(anyString())).thenReturn(false);

        // Act
        boolean result = aiRecommendationValidator.isValidRecommendation(recommendation);

        // Assert
        assertFalse(result);
        verify(slotRepository, times(1)).existsById("SLT-001");
    }
    private AIRecommendationValidator aiRecommendationValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
}