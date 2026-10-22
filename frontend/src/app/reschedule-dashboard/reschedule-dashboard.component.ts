import { Component, OnInit } from '@angular/core';
import { RescheduleSuggestion } from '../models/reschedule-suggestion.model';
import { RescheduleSuggestionService } from '../services/reschedule-suggestion.service';

@Component({
  selector: 'app-reschedule-dashboard',
  templateUrl: './reschedule-dashboard.component.html',
  styleUrls: ['./reschedule-dashboard.component.css']
})
export class RescheduleDashboardComponent implements OnInit {
  suggestions: RescheduleSuggestion[] = [];
  loading = false;

  constructor(private suggestionService: RescheduleSuggestionService) { }

  ngOnInit(): void {
    this.loadSuggestions();
  }

  loadSuggestions(): void {
    this.loading = true;
    this.suggestionService.getSuggestions().subscribe({
      next: (suggestions) => {
        this.suggestions = suggestions;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error loading suggestions:', error);
        this.loading = false;
      }
    });
  }

  refreshSuggestions(): void {
    this.loadSuggestions();
  }

  onAcceptSuggestion(id: number): void {
    this.suggestionService.acceptSuggestion(id).subscribe({
      next: (updatedSuggestion) => {
        // Update the suggestion in the list
        const index = this.suggestions.findIndex(s => s.id === id);
        if (index !== -1) {
          this.suggestions[index] = updatedSuggestion;
        }
      },
      error: (error) => {
        console.error('Error accepting suggestion:', error);
      }
    });
  }

  onRejectSuggestion(id: number): void {
    this.suggestionService.rejectSuggestion(id).subscribe({
      next: (updatedSuggestion) => {
        // Update the suggestion in the list
        const index = this.suggestions.findIndex(s => s.id === id);
        if (index !== -1) {
          this.suggestions[index] = updatedSuggestion;
        }
      },
      error: (error) => {
        console.error('Error rejecting suggestion:', error);
      }
    });
  }
}