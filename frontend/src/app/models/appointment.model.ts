export interface Appointment {
  id: string;
  patientName: string;
  appointmentType: string;
  providerId: string;
  providerName: string;
  slotId: string;
  slotStartTime: string; // ISO date string
  slotDurationMinutes: number;
  status: string;
  createdAt: string; // ISO date string
}