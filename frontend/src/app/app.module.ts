import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { RescheduleDashboardComponent } from './reschedule-dashboard/reschedule-dashboard.component';
import { RescheduleSuggestionComponent } from './reschedule-suggestion/reschedule-suggestion.component';

@NgModule({
  declarations: [
    AppComponent,
    RescheduleDashboardComponent,
    RescheduleSuggestionComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }