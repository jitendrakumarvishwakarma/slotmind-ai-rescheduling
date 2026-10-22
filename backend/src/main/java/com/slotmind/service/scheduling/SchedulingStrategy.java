package com.slotmind.service.scheduling;

import com.slotmind.model.Appointment;
import com.slotmind.model.Slot;
import java.util.Optional;

public interface SchedulingStrategy {
    Optional<Slot> findBestSlot(Appointment appointment, String triggerReason);
    String getStrategyName();
}