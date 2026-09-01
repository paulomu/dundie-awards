# Findings

1. Missing Springdoc OpenAPI dependency
    1. Why it matters: It auto generates the OpenAPI schema and makes it easier to test endpoints either through the built-in web ui (/swagger-ui.html) or by import into Postman from the /openapi URL as a new collection.
    2. Action items:
        1. [x] Add the dependency to build.gradle
2. There's no test coverage for existing code.
    1. Why it matters: Risk of introducing changes and breaking existing behavior. Tests are also a way of documenting existing requirements and therefore should exist.
    2. Action items:
        1. [x] Add EmployeeControllerIntegrationTest.
3. There's no standardized API error handling.
    1. Why it matters: There's code duplication (return HTTP 404 when Employee not found) across different endpoints. Not leveraging Spring features for this purpose.
    2. Action items:
        1. [x] Throw an EmployeeNotFoundException when the EmployeeRepository can't find an Employee by it's id. 
        2. [x] Start simple by adding an @ExceptionHandler method in the EmployeeController.
        3. [x] Refactor and improve by moving that @ExceptionHandler into a new GlobalExceptionHandler controller advice class.
4. There's no service layer in the project to host business logic.
    1. Why it matters: Separation of concerns; Controller should be responsible for the public API layer and guarantee it's contract (request/response models, error codes, auth, etc), while Service should deal with domain-level objects, repositories and other services.
    2. Action items:
        - [x] Refactor EmployeeController
            - [x] Move business logic into new EmployeeService.
            - [x] Replace EmployeeRepository dependency with EmployeeService.
5. The EmployeeController has an unused ActivityRepository dependency.
   1. Why it matters: The Activity class has an occuredAt and event fields suggesting this is an append-only audit log.
   2. Action items:
      1. [x] Move ActivityRepository dependency into EmployeeService.
      2. [x] Save Activities into ActivityRepository whenever a relevant event happens to an Employee.
      3. [x] Update the EmployeeControllerIntegrationTest to cover those changes.
6. There's no API to manage Organizations.
    1. Why it matters: API users are not able to tell, create or modify organizations before they add Employees.
    2. Action items:
        1. [x] Add OrganizationService
        2. [x] Add OrganizationController
        3. [x] Add OrganizationControllerIntegrationTest
7. The API doesn't let update an Employee's organization or dundieAwards after creating it.
    1. Skipped, reason: requirement is unconfirmed; might there be a reason for that (eg. immutable org, dundie awards calculated in a different way and not settable, etc)
8. Source code not taking advantage of some Java features
   1. Why it matters: Java has introduced 
   2. Action items:
      1. [x] Refactor the code to 
          1. Optional fluent API (Optional.map.orElse...)
          2. var keyword
          3. Java records for DTOs
9. Move to Postgresql
   1. Why it matters: Current database (H2) runs in memory and is not persistent. Any changes are discarded after the application reboots.
   2. Action items:
      1. [x] Add the PostgreSQL JDBC driver.
      2. [x] Configure the application to connect to PostgreSQL.
      3. [x] Configure Hibernate to update the PostgreSQL schema.
      4. [x] Add Testcontainers PostgreSQL support to integration tests.
10. Application is not containerized
    1. Why it matters: Packing backend microservices as container images is the industry standard way for distributing and horizontally deploying the application through an orchestrator such as ECS or Kubernetes. It also makes it easier to quickly run the application on local dev machines!
    2. Action items
        1. [x] Add Dockerfile
        2. [x] Add docker-compose.yml file
11. Spring Boot version is EOL.
    1. Why it matters:
       1. Security issues 
       2. No vendor support 
       3. Lack of most recent features
    2. Action items:
       1. [ ] Upgrade to 3.5.x (Enterprise EOL 2032) -> skipped for now
