export interface Slot {
  id: string;
  providerId: string;
  providerName: string;
  startTime: string; // ISO date string
  durationMinutes: number;
  status: string;
}