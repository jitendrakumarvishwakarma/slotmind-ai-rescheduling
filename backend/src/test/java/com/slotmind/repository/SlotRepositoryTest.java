package com.slotmind.repository;

import com.slotmind.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SlotRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SlotRepository slotRepository;

    @Test
    void testFindOpenSlotsForProviderAfterTime() {
        // Arrange
        Provider provider = new Provider("PRV-001", "Dr. Smith", Specialty.GENERAL, ProviderStatus.AVAILABLE);
        entityManager.persist(provider);

        Slot pastSlot = new Slot("SLT-001", provider, LocalDateTime.now().minusHours(1), 30, SlotStatus.OPEN);
        Slot openSlot1 = new Slot("SLT-002", provider, LocalDateTime.now().plusHours(1), 30, SlotStatus.OPEN);
        Slot openSlot2 = new Slot("SLT-003", provider, LocalDateTime.now().plusHours(2), 30, SlotStatus.OPEN);
        Slot bookedSlot = new Slot("SLT-004", provider, LocalDateTime.now().plusHours(3), 30, SlotStatus.BOOKED);

        entityManager.persist(pastSlot);
        entityManager.persist(openSlot1);
        entityManager.persist(openSlot2);
        entityManager.persist(bookedSlot);
        entityManager.flush();

        // Act
        List<Slot> result = slotRepository.findOpenSlotsForProviderAfterTime(provider, SlotStatus.OPEN, LocalDateTime.now());

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).extracting(Slot::getId).containsExactlyInAnyOrder("SLT-002", "SLT-003");
    }

    @Test
    void testFindOpenSlotsBySpecialtyAfterTime() {
        // Arrange
        Provider provider1 = new Provider("PRV-001", "Dr. Smith", Specialty.GENERAL, ProviderStatus.AVAILABLE);
        Provider provider2 = new Provider("PRV-002", "Dr. Jones", Specialty.GENERAL, ProviderStatus.AVAILABLE);
        Provider provider3 = new Provider("PRV-003", "Dr. Brown", Specialty.CARDIOLOGY, ProviderStatus.AVAILABLE);
        
        entityManager.persist(provider1);
        entityManager.persist(provider2);
        entityManager.persist(provider3);

        Slot slot1 = new Slot("SLT-001", provider1, LocalDateTime.now().plusHours(1), 30, SlotStatus.OPEN);
        Slot slot2 = new Slot("SLT-002", provider2, LocalDateTime.now().plusHours(2), 30, SlotStatus.OPEN);
        Slot slot3 = new Slot("SLT-003", provider3, LocalDateTime.now().plusHours(3), 30, SlotStatus.OPEN);
        Slot closedSlot = new Slot("SLT-004", provider1, LocalDateTime.now().plusHours(4), 30, SlotStatus.BOOKED);

        entityManager.persist(slot1);
        entityManager.persist(slot2);
        entityManager.persist(slot3);
        entityManager.persist(closedSlot);
        entityManager.flush();

        // Act
        List<Slot> result = slotRepository.findOpenSlotsBySpecialtyAfterTime("GENERAL", SlotStatus.OPEN, LocalDateTime.now());

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).extracting(Slot::getId).containsExactlyInAnyOrder("SLT-001", "SLT-002");
    }
}