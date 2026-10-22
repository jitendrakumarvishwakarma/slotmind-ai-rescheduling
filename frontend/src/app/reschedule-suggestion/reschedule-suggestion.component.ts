import { Component, Input, Output, EventEmitter } from '@angular/core';
import { RescheduleSuggestion } from '../models/reschedule-suggestion.model';

@Component({
  selector: 'app-reschedule-suggestion',
  templateUrl: './reschedule-suggestion.component.html',
  styleUrls: ['./reschedule-suggestion.component.css']
})
export class RescheduleSuggestionComponent {
  @Input() suggestion!: RescheduleSuggestion;
  @Output() accept = new EventEmitter<number>();
  @Output() reject = new EventEmitter<number>();

  getConfidenceClass(): string {
    if (this.suggestion.confidenceScore >= 0.8) {
      return 'confidence-high';
    } else if (this.suggestion.confidenceScore >= 0.6) {
      return 'confidence-medium';
    } else {
      return 'confidence-low';
    }
  }

  getFormattedDate(dateString: string): string {
    const date = new Date(dateString);
    return date.toLocaleString();
  }

  onAccept(): void {
    this.accept.emit(this.suggestion.id);
  }

  onReject(): void {
    this.reject.emit(this.suggestion.id);
  }
}