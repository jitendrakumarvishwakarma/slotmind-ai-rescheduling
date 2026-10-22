package com.slotmind.ai;

import com.slotmind.model.Appointment;
import com.slotmind.model.Slot;
import java.util.Optional;

public interface LLMGateway {
    /**
     * Call the LLM service to get a rescheduling recommendation
     * @param appointment The appointment that needs rescheduling
     * @param triggerReason The reason for rescheduling (e.g., PROVIDER_OFFLINE, SLOT_CANCELLED)
     * @return AI-generated recommendation with confidence score and explanation
     */
    Optional<AIRecommendation> getReschedulingRecommendation(Appointment appointment, String triggerReason);
}