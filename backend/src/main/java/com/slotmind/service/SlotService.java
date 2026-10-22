package com.slotmind.service;

import com.slotmind.model.Slot;
import com.slotmind.model.SlotStatus;
import java.util.List;
import java.util.Optional;

public interface SlotService {
    List<Slot> getAllSlots();
    Optional<Slot> getSlotById(String id);
    Slot createSlot(Slot slot);
    Slot updateSlot(String id, Slot slotDetails);
    void deleteSlot(String id);
    List<Slot> getSlotsByStatus(SlotStatus status);
    List<Slot> getOpenSlotsForProvider(String providerId);
    List<Slot> getOpenSlotsBySpecialty(String specialty);
}