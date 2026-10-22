import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RescheduleSuggestion } from '../models/reschedule-suggestion.model';

@Injectable({
  providedIn: 'root'
})
export class RescheduleSuggestionService {
  private apiUrl = 'http://localhost:8080/api/reschedule-suggestions';

  constructor(private http: HttpClient) { }

  getSuggestions(): Observable<RescheduleSuggestion[]> {
    return this.http.get<RescheduleSuggestion[]>(this.apiUrl);
  }

  getSuggestionById(id: number): Observable<RescheduleSuggestion> {
    return this.http.get<RescheduleSuggestion>(`${this.apiUrl}/${id}`);
  }

  generateSuggestion(appointmentId: string, triggerReason: string): Observable<RescheduleSuggestion> {
    return this.http.post<RescheduleSuggestion>(`${this.apiUrl}/generate`, null, {
      params: {
        appointmentId: appointmentId,
        triggerReason: triggerReason
      }
    });
  }

  acceptSuggestion(id: number): Observable<RescheduleSuggestion> {
    return this.http.post<RescheduleSuggestion>(`${this.apiUrl}/${id}/accept`, {});
  }

  rejectSuggestion(id: number): Observable<RescheduleSuggestion> {
    return this.http.post<RescheduleSuggestion>(`${this.apiUrl}/${id}/reject`, {});
  }
}