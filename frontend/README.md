# SlotMind - AI Re-Scheduling Engine (Frontend)

This is the frontend component of the SlotMind AI Re-Scheduling Engine, built with Angular 17.

## Prerequisites

- Node.js 18+ or higher
- npm 9+ or higher

## Project Structure

```
frontend/
├── src/
│   ├── app/
│   │   ├── models/
│   │   ├── reschedule-dashboard/
│   │   ├── reschedule-suggestion/
│   │   ├── services/
│   │   ├── app.component.*
│   │   └── app.module.ts
│   ├── assets/
│   ├── environments/
│   ├── index.html
│   ├── main.ts
│   └── styles.css
├── angular.json
├── package.json
└── tsconfig.json
```

## Key Features

- Dashboard displaying rescheduling suggestions with patient information
- Visual indicators for confidence levels (high/medium/low)
- Detailed view of current and proposed appointment slots
- Clear display of trigger reasons (PROVIDER_OFFLINE, SLOT_CANCELLED)
- Patient-friendly explanations from AI recommendations
- Accept/Reject functionality for suggestions with immediate feedback
- Status tracking (PENDING, ACCEPTED, REJECTED)
- Responsive design for desktop and tablet use

## Getting Started

1. Install dependencies:
   ```bash
   npm install
   ```

2. Start the development server:
   ```bash
   npm start
   ```

3. Navigate to `http://localhost:4200/` in your browser.

Note: The backend must be running on `http://localhost:8080` for the frontend to work properly.

## Features

- **Rescheduling Dashboard**: Central hub displaying all rescheduling suggestions
- **Confidence Visualization**: Color-coded confidence scores (green=high, yellow=medium, red=low)
- **Detailed Information**: Shows patient name, current appointment, proposed slot, and provider details
- **Action Controls**: Accept/Reject buttons with visual feedback on suggestion status
- **Responsive Design**: Works well on different screen sizes
- **Real-time Updates**: Refresh button to get latest suggestions from backend

## Development

### Code Scaffolding

Run `ng generate component component-name` to generate a new component.

### Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

### Running Tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

Run `ng e2e` to execute the end-to-end tests via a platform of your choice.

### Further Help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.io/cli) page.

## Architecture Decisions

See [ADR.md](../ADR.md) for detailed architecture decisions and trade-offs.