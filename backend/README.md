# SlotMind - AI Re-Scheduling Engine (Backend)

This is the backend component of the SlotMind AI Re-Scheduling Engine, built with Spring Boot.

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Project Structure

```
backend/
├── src/main/java/com/slotmind/
│   ├── SlotmindApplication.java
│   ├── config/
│   ├── controller/
│   ├── model/
│   ├── repository/
│   ├── service/
│   └── dto/
├── src/main/resources/
│   ├── application.properties
│   └── data.sql
└── pom.xml
```

## Key Features

- RESTful API for managing providers, slots, appointments, and rescheduling suggestions
- Hybrid scheduling engine combining AI-powered recommendations with rule-based fallback
- Asynchronous rescheduling workflow triggered by provider status changes or slot cancellations
- Transactional acceptance of suggestions to prevent double booking
- Duplicate suggestion prevention for the same appointment and trigger reason
- Confidence scoring for AI recommendations with clear visual indicators
- H2 in-memory database for easy local execution

## Getting Started

1. Clone the repository
2. Navigate to the backend directory
3. Build the project:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## API Endpoints

### Providers
- `GET /api/providers` - Get all providers
- `GET /api/providers/{id}` - Get provider by ID
- `POST /api/providers` - Create a new provider
- `PUT /api/providers/{id}` - Update provider by ID
- `DELETE /api/providers/{id}` - Delete provider by ID
- `PUT /api/providers/{id}/status` - Update provider status

### Slots
- `GET /api/slots` - Get all slots
- `GET /api/slots/{id}` - Get slot by ID
- `POST /api/slots` - Create a new slot
- `PUT /api/slots/{id}` - Update slot by ID
- `DELETE /api/slots/{id}` - Delete slot by ID
- `PUT /api/slots/{id}/status` - Update slot status

### Appointments
- `GET /api/appointments` - Get all appointments
- `GET /api/appointments/{id}` - Get appointment by ID
- `POST /api/appointments` - Create a new appointment
- `PUT /api/appointments/{id}` - Update appointment by ID
- `DELETE /api/appointments/{id}` - Delete appointment by ID

### Reschedule Suggestions
- `GET /api/reschedule-suggestions` - Get all reschedule suggestions
- `GET /api/reschedule-suggestions/{id}` - Get reschedule suggestion by ID
- `POST /api/reschedule-suggestions` - Create a new reschedule suggestion
- `POST /api/reschedule-suggestions/generate` - Generate reschedule suggestion for appointment
- `POST /api/reschedule-suggestions/{id}/accept` - Accept reschedule suggestion
- `POST /api/reschedule-suggestions/{id}/reject` - Reject reschedule suggestion

## Database

The application uses an in-memory H2 database for easy local execution. The database is initialized with sample data from `data.sql`.

To access the H2 console:
1. Start the application
2. Navigate to http://localhost:8080/h2-console
3. Use the following settings:
   - JDBC URL: jdbc:h2:mem:testdb
   - User Name: sa
   - Password: (leave blank)

## Seed Data

The application is pre-populated with sample data including:
- 5 providers with different specialties and statuses
- 6 slots with various statuses and times
- 4 confirmed appointments

## Testing

To run tests:
```bash
mvn test
```

The test suite includes:
- Unit tests for scheduling strategies
- Integration tests for services
- Repository tests for custom queries
- Controller tests for REST endpoints
- AI component tests with mocking

## Building

To build the project:
```bash
mvn clean install
```

## Running

To run the application:
```bash
mvn spring-boot:run
```

Or run the jar file after building:
```bash
java -jar target/slotmind-ai-rescheduling-0.0.1-SNAPSHOT.jar
```

## Architecture Decisions

See [ADR.md](../ADR.md) for detailed architecture decisions and trade-offs.