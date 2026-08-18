# Enterprise CRM Microservices Platform

Production-oriented Enterprise CRM built with Spring Boot microservices, React, MySQL, JWT/RBAC, Eureka, API Gateway, centralized configuration and Docker.

## Repositories

- Architecture: https://github.com/DineshGupta-cloud/crm-microservices-architecture
- Implementation: https://github.com/DineshGupta-cloud/crm-microservices-services

## Architecture

```text
React / Vite
     |
     v
API Gateway :8080
     |
     +---- Auth Service :8081 ---- auth_db
     +---- Company Service :8082 - company_db
     +---- Branch Service :8083 -- branch_db
     +---- Department :8084 ------ department_db
     +---- Designation :8085 ------ designation_db
     +---- Employee :8086 -------- employee_db

Config Server + Eureka Discovery
```

## Services

| Service | Responsibility | Port |
|---|---|---:|
| config-server | Central configuration | - |
| discovery-server | Eureka discovery | 8761 |
| api-gateway | Routing / edge | 8080 |
| common-lib | Shared platform classes | - |
| auth-service | JWT authentication / RBAC | 8081 |
| company-service | Company management | 8082 |
| branch-service | Branch management | 8083 |
| department-service | Department management | 8084 |
| designation-service | Designation management | 8085 |
| employee-service | Employee management | 8086 |
| lead-service | Lead management | TBD |
| customer-service | Customer management | TBD |
| vendor-service | Vendor management | TBD |
| product-service | Product management | TBD |
| task-service | Tasks and activities | TBD |
| notification-service | Notifications | TBD |
| audit-service | Audit trail | TBD |

## Organization Domain

```text
Company
  |
  +-- Branch
       |
       +-- Department
            |
            +-- Designation
            |
            +-- Employee
```

Cross-service relationships use IDs. There are no cross-database JPA relationships.

## Technology

- Java 17
- Spring Boot 3.4.x
- Spring Cloud 2024.x
- Spring Security
- JWT / JJWT
- Spring Data JPA
- MySQL 8
- Maven
- Eureka
- Spring Cloud Gateway
- Spring Cloud Config
- Bean Validation
- Lombok
- Docker
- JUnit
- React + Vite

## Authentication

Auth service provides registration, login, BCrypt password hashing, JWT access tokens, refresh tokens, stateless Spring Security, roles and permissions.

```text
POST /api/auth/register
POST /api/auth/login
POST /api/auth/refresh
```

## CRUD APIs

```text
/api/v1/companies
/api/v1/branches
/api/v1/departments
/api/v1/designations
/api/v1/employees
```

Standard operations:

```text
GET    /api/v1/{resource}
GET    /api/v1/{resource}/{id}
POST   /api/v1/{resource}
PUT    /api/v1/{resource}/{id}
DELETE /api/v1/{resource}/{id}
```

## Database Strategy

Each microservice owns its database/schema. A service must never query another service's database directly.

```text
auth_db
company_db
branch_db
department_db
designation_db
employee_db
lead_db
customer_db
vendor_db
product_db
task_db
notification_db
audit_db
```

## Build

Prerequisites: JDK 17+, Maven 3.9+, MySQL 8+. Docker is optional.

```bash
mvn clean install
```

Start infrastructure in this order:

```text
1. config-server
2. discovery-server
3. api-gateway
4. auth-service
5. business services
```

Discovery dashboard:

```text
http://localhost:8761
```

Gateway:

```text
http://localhost:8080
```

## Configuration

Never commit production passwords or JWT secrets. Use environment variables or external configuration:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
JWT_SECRET
JWT_ACCESS_EXPIRATION
JWT_REFRESH_EXPIRATION
```

## Development Rules

1. Database per service.
2. No direct database access between services.
3. DTOs at API boundaries.
4. Validate requests before persistence.
5. JWT authentication at the platform boundary.
6. RBAC and permissions for authorization.
7. Consistent API response and error structures.
8. Unit and integration tests for production services.
9. REST for synchronous communication.
10. Events for future asynchronous workflows.

## Repository Synchronization

The architecture repository defines service boundaries and roadmap. This implementation repository contains the corresponding code.

Architecture repository:
https://github.com/DineshGupta-cloud/crm-microservices-architecture

Services repository:
https://github.com/DineshGupta-cloud/crm-microservices-services

## Status

Foundation:
- Config Server
- Eureka Discovery
- API Gateway
- Common Library
- Auth / JWT / RBAC

Organization domain:
- Company
- Branch
- Department
- Designation
- Employee

Planned CRM domains:
- Lead
- Customer
- Vendor
- Product
- Task
- Notification
- Audit
