# CRM Backend AI Development Instructions

This repository contains the Spring Boot microservices implementation for the Enterprise CRM.

## Stack
- Java 17
- Spring Boot 3.4.x
- Spring Cloud 2024.x
- Maven
- Spring Security
- JWT/JJWT
- Spring Data JPA
- MySQL 8
- Eureka
- Spring Cloud Gateway
- Spring Cloud Config
- Bean Validation
- Lombok
- JUnit

## Rules
1. Read the architecture repository documentation before architectural changes.
2. Inspect the existing service before modifying it.
3. Follow Controller -> Service -> Repository layering.
4. Use DTOs at API boundaries.
5. Do not directly access another service's database.
6. Cross-service relationships use IDs.
7. Validate input.
8. Use consistent exception handling and HTTP status codes.
9. Paginate potentially large collections.
10. Avoid N+1 queries.
11. Use transactions deliberately.
12. Never commit credentials or secrets.
13. Do not modify unrelated services.
14. Reuse common-lib functionality when appropriate.
15. Add tests for new behaviour.

## Service Development
For a new service:
1. Define responsibility and database ownership.
2. Define REST contract.
3. Add entity/DTO/repository/service/controller.
4. Add validation and exception handling.
5. Add security/permissions.
6. Add tests.
7. Add configuration/discovery.
8. Add gateway route.
9. Update architecture documentation.

## Before finishing
Run the relevant Maven tests and inspect the git diff. Report files changed, tests, risks, and next step.
