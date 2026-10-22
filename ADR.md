# Architecture Decision Records (ADRs)

## 1. Hybrid Scheduling Strategy with AI and Rule-Based Fallback

**Status:** Accepted

**Context:** We needed a reliable scheduling system that leverages AI for intelligent recommendations while ensuring availability during AI service failures.

**Decision:** Implemented a hybrid approach where AI-powered recommendations are tried first, with a rule-based fallback for reliability.

**Consequences:** 
- Pros: Best of both worlds - intelligence when AI works, reliability when it doesn't
- Cons: Increased complexity in managing two scheduling strategies

## 2. Asynchronous Rescheduling Workflow

**Status:** Accepted

**Context:** Providers going offline or slots being cancelled needed to trigger rescheduling automatically.

**Decision:** Implemented event-driven, asynchronous workflows using Spring's scheduling capabilities.

**Consequences:**
- Pros: Non-blocking operations, better user experience
- Cons: Increased complexity in tracking workflow state

## 3. Transactional Suggestion Acceptance

**Status:** Accepted

**Context:** Need to prevent double-booking when accepting rescheduling suggestions.

**Decision:** Used database transactions to atomically update appointment and slot statuses.

**Consequences:**
- Pros: Data consistency guaranteed, prevents race conditions
- Cons: Potential performance impact during high concurrency

## 4. Duplicate Suggestion Prevention

**Status:** Accepted

**Context:** Multiple triggers could create duplicate suggestions for the same appointment.

**Decision:** Implemented database-level constraints to prevent duplicate pending suggestions.

**Consequences:**
- Pros: Data integrity maintained, reduced user confusion
- Cons: Additional database queries for validation

## 5. Pluggable Scheduling Strategy Architecture

**Status:** Accepted

**Context:** Future-proofing for different scheduling algorithms.

**Decision:** Used Strategy pattern with Spring dependency injection for scheduling approaches.

**Consequences:**
- Pros: Easy to extend with new algorithms, clean separation of concerns
- Cons: Slight overhead in architecture complexity

## 6. DTO Pattern for API Responses

**Status:** Accepted

**Context:** Need to control data exposure to frontend and optimize payload size.

**Decision:** Implemented Data Transfer Objects (DTOs) for all API responses.

**Consequences:**
- Pros: Better security, reduced payload size, cleaner API contracts
- Cons: Additional mapping layer increases complexity

## 7. Mock AI Gateway for Development

**Status:** Accepted

**Context:** Enable development without external AI service dependencies.

**Decision:** Created mock LLM gateway implementation for local development and testing.

**Consequences:**
- Pros: Faster development cycle, easier testing, works offline
- Cons: Mock behavior may not reflect real AI service characteristics

## 8. Confidence Score System

**Status:** Accepted

**Context:** Users need to understand recommendation reliability.

**Decision:** Implemented confidence scoring (0.0-1.0) with visual indicators.

**Consequences:**
- Pros: Transparent AI decision-making, user trust building
- Cons: Requires careful calibration of scores

## 9. Frontend Technology Stack

**Status:** Accepted

**Context:** Need modern, responsive UI framework for dashboard.

**Decision:** Chose Angular 17 with TypeScript for frontend development.

**Consequences:**
- Pros: Strong typing, component-based architecture, extensive ecosystem
- Cons: Steeper learning curve, larger bundle size than simpler frameworks

## 10. H2 Database for Development

**Status:** Accepted

**Context:** Simplify local development and testing setup.

**Decision:** Used H2 in-memory database with schema initialization.

**Consequences:**
- Pros: Zero setup, fast, easy testing
- Cons: Different from production database, potential compatibility issues