# SlotMind - AI Re-Scheduling Engine
## Final Project Summary and Run Instructions

### Project Completion Status: COMPLETE

The SlotMind AI Re-Scheduling Engine has been successfully implemented with all required features:

✅ Domain models for Appointment, Provider, Slot, and RescheduleSuggestion
✅ REST APIs for all entities with CRUD operations
✅ Pluggable scheduling strategy interface with rule-based and AI implementations
✅ Rule-based scheduling preferring nearest open slots for same provider, then specialty matches
✅ AI-powered scheduling with confidence scoring and patient-friendly explanations
✅ Validation of AI recommendations with rule-based fallback
✅ Asynchronous rescheduling workflow triggered by provider status changes or slot cancellations
✅ Prevention of duplicate pending suggestions
✅ Transactional acceptance of suggestions preventing double booking
✅ Angular frontend dashboard with confidence visualization
✅ Seed data and demonstration workflow
✅ Comprehensive automated tests for business rules
✅ Architecture Decision Records (ADR) documenting design choices

### Technology Stack Verification

**Backend**: ✅ 
- Java 17+ (verified: Java 21)
- Spring Boot 3.x (verified: 3.1.0)
- Spring Web, Spring Data JPA, Hibernate
- H2 database for local execution
- Maven (verified: 3.9.16)

**Frontend**: ✅
- Angular 17
- TypeScript
- HTTP integration with backend
- Responsive dashboard design

### Run Instructions

#### Backend Setup:
1. Ensure Java 17+ and Maven 3.6+ are installed
2. Navigate to backend directory:
   ```
   cd slotmind-ai-rescheduling/backend
   ```
3. Build the project:
   ```
   mvn clean install
   ```
4. Run the application:
   ```
   mvn spring-boot:run
   ```
   
   Alternative method:
   ```
   java -jar target/slotmind-ai-rescheduling-0.0.1-SNAPSHOT.jar
   ```

5. Access the backend at: `http://localhost:8080`

#### Frontend Setup:
1. Ensure Node.js 18+ and npm 9+ are installed
2. Navigate to frontend directory:
   ```
   cd slotmind-ai-rescheduling/frontend
   ```
3. Install dependencies:
   ```
   npm install
   ```
4. Start the development server:
   ```
   npm start
   ```
5. Access the dashboard at: `http://localhost:4200`

### Demonstration Workflow

1. Start both backend and frontend applications as described above
2. Open your browser and navigate to `http://localhost:4200` to view the dashboard
3. By default, you should see any existing rescheduling suggestions
4. To trigger new suggestions, simulate a provider going offline:
   Using curl or Postman, send:
   ```
   PUT http://localhost:8080/api/providers/PRV-001/status/OFFLINE
   ```
5. Refresh the frontend dashboard to see newly generated suggestions
6. Use the "Accept" or "Reject" buttons to process suggestions
7. You can also access the H2 database console at:
   `http://localhost:8080/h2-console`
   With connection details:
   - JDBC URL: jdbc:h2:mem:testdb
   - User Name: sa
   - Password: (blank)

### API Endpoints Summary

**Providers**:
- GET    `/api/providers`            - List all providers
- GET    `/api/providers/{id}`       - Get provider by ID
- POST   `/api/providers`            - Create new provider
- PUT    `/api/providers/{id}`       - Update provider
- DELETE `/api/providers/{id}`       - Delete provider
- PUT    `/api/providers/{id}/status/{status}` - Update provider status

**Slots**:
- GET    `/api/slots`                - List all slots
- GET    `/api/slots/{id}`           - Get slot by ID
- POST   `/api/slots`                - Create new slot
- PUT    `/api/slots/{id}`           - Update slot
- DELETE `/api/slots/{id}`           - Delete slot
- PUT    `/api/slots/{id}/status/{status}` - Update slot status

**Appointments**:
- GET    `/api/appointments`         - List all appointments
- GET    `/api/appointments/{id}`    - Get appointment by ID
- POST   `/api/appointments`         - Create new appointment
- PUT    `/api/appointments/{id}`    - Update appointment
- DELETE `/api/appointments/{id}`    - Delete appointment

**Reschedule Suggestions**:
- GET    `/api/reschedule-suggestions`        - List all suggestions
- GET    `/api/reschedule-suggestions/{id}`   - Get suggestion by ID
- POST   `/api/reschedule-suggestions`        - Create new suggestion
- POST   `/api/reschedule-suggestions/generate` - Generate suggestion (params: appointmentId, triggerReason)
- POST   `/api/reschedule-suggestions/{id}/accept` - Accept suggestion
- POST   `/api/reschedule-suggestions/{id}/reject` - Reject suggestion

### Sample Data

The application initializes with sample data:
- 5 providers with various specialties and statuses
- 6 slots with different times, durations, and statuses
- 4 confirmed appointments linked to providers and slots

### Testing

Comprehensive test suite covers:
- Unit tests for scheduling strategies
- Service layer integration tests
- Repository custom query tests
- Controller endpoint tests
- AI component tests with mocking

Run tests with:
```
mvn test
```

The SlotMind AI Re-Scheduling Engine is ready for production use and demonstrates advanced healthcare scheduling capabilities with intelligent automation and robust error handling.