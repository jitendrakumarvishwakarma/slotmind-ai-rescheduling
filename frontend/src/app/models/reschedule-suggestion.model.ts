export interface RescheduleSuggestion {
  id: number;
  appointmentId: string;
  patientName: string;
  currentProviderId: string;
  currentProviderName: string;
  newSlotId: string;
  newProviderId: string;
  newProviderName: string;
  newSlotStartTime: string; // ISO date string
  newSlotDurationMinutes: number;
  triggerReason: string;
  confidenceScore: number;
  explanation: string;
  status: string;
  createdAt: string; // ISO date string
  updatedAt: string; // ISO date string
}