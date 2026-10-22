# SlotMind - AI Re-Scheduling Engine

SlotMind is an intelligent re-scheduling engine designed for healthcare facilities that automatically suggests alternative appointments when providers go offline or slots become unavailable.

## Project Overview

This project demonstrates a complete healthcare appointment re-scheduling solution with:
- AI-powered recommendation engine with rule-based fallback
- Asynchronous event-driven rescheduling workflows
- Transactional safety to prevent double booking
- Modern Angular frontend with intuitive dashboard
- Comprehensive test coverage

## Technology Stack

### Backend
- Java 17+
- Spring Boot 3.x
- Spring Web, Spring Data JPA, Hibernate
- H2 database for easy local execution
- Maven
- JUnit and Mockito for testing

### Frontend
- Angular 17
- TypeScript
- Responsive design

## Project Structure

```
slotmind-ai-rescheduling/
├── backend/           # Spring Boot application
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/slotmind/
│   │   │   │   ├── controller/
│   │   │   │   ├── model/
│   │   │   │   ├── repository/
│   │   │   │   ├── service/
│   │   │   │   ├── dto/
│   │   │   │   └── config/
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
├── frontend/          # Angular application
│   ├── src/
│   │   ├── app/
│   │   │   ├── models/
│   │   │   ├── services/
│   │   │   ├── reschedule-dashboard/
│   │   │   ├── reschedule-suggestion/
│   │   │   └── app.module.ts
│   │   └── assets/
│   ├── angular.json
│   └── package.json
├── ADR.md             # Architecture Decision Records
└── README.md          # This file
```

## Key Features

### Intelligent Scheduling
- **AI-Powered Recommendations**: Leverages machine learning to suggest optimal rescheduling options
- **Rule-Based Fallback**: Reliable rule-based scheduling when AI is unavailable
- **Confidence Scoring**: Clear indicators of recommendation reliability (0.0-1.0)

### Automated Workflows
- **Event-Driven Rescheduling**: Automatically triggered when providers go OFFLINE or slots are CANCELLED
- **Duplicate Prevention**: Prevents multiple suggestions for the same appointment and trigger
- **Asynchronous Processing**: Non-blocking operations for better user experience

### Data Safety
- **Transactional Acceptance**: Atomic updates to prevent double booking
- **Validation**: Comprehensive validation of AI recommendations
- **Status Tracking**: Clear tracking of suggestion lifecycle (PENDING, ACCEPTED, REJECTED)

### User Experience
- **Frontend Dashboard**: Clean, responsive interface for front-desk staff
- **Visual Indicators**: Color-coded confidence levels and status badges
- **Detailed Information**: Patient information, provider details, and explanations
- **Simple Actions**: One-click accept/reject functionality

## Getting Started

### Prerequisites
- Java 17+
- Node.js 18+
- Maven 3.6+

### Backend Setup

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Access the application at `http://localhost:8080`

### Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm start
   ```

4. Access the dashboard at `http://localhost:4200`

### Demo Workflow

1. Start both backend and frontend applications
2. Open the frontend dashboard at `http://localhost:4200`
3. View existing rescheduling suggestions
4. Set a provider's status to OFFLINE to trigger new suggestions:
   ```bash
   curl -X PUT http://localhost:8080/api/providers/PRV-001/status/OFFLINE
   ```
5. Refresh the dashboard to see new suggestions
6. Accept or reject suggestions to update appointment schedules

## API Documentation

The backend exposes RESTful APIs for all entities:
- Providers: `/api/providers`
- Slots: `/api/slots`
- Appointments: `/api/appointments`
- Reschedule Suggestions: `/api/reschedule-suggestions`

Full API documentation is available in the backend README.

## Testing

The project includes comprehensive test coverage:
- Unit tests for business logic
- Integration tests for services
- Repository tests for database queries
- Controller tests for REST endpoints
- AI component tests with mocking

Run backend tests with:
```bash
mvn test
```

## Architecture Decisions

See [ADR.md](ADR.md) for detailed architecture decisions and trade-offs made during development.

## Future Enhancements

1. **Advanced AI Integration**: Connect to real LLM services for improved recommendations
2. **Notification System**: Email/SMS notifications for patients about schedule changes
3. **Calendar Integration**: Sync with popular calendar applications
4. **Analytics Dashboard**: Track rescheduling patterns and system performance
5. **Multi-language Support**: Localization for diverse healthcare facilities
6. **Mobile Application**: Native mobile apps for patients and staff