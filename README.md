# DDD + Hexagonal Architecture — Complete Project (Java 21)

A runnable **single-service application**, independent of the Saga project. The domain layer has no dependencies on Spring or JPA.

## Architecture

- `domain`: Aggregate Root `Order`, Value Object `Money`, domain invariants, and state management.
- `application.port.in`: Input ports and use case commands.
- `application.port.out`: Repository output port.
- `application.OrderService`: Use case orchestration and transaction boundary.
- `adapter.in.rest`: REST controllers, DTOs, validation, and Problem Details.
- `adapter.out.jpa`: JPA adapter with H2 database.

Refer to the **six PNG diagrams** in the `diagrams/` directory and the Word document in the `documentazione/` directory.

## Prerequisites

Java 21, Maven 3.9+; Docker is optional.

## Build and Test

```bash
mvn clean test
mvn spring-boot:run
```

Alternatively:

```bash
docker compose up --build
```

## REST API Examples

```bash
curl -i -X POST http://localhost:8080/api/v1/orders \
  -H 'Content-Type: application/json' \
  -d '{"product":"Book","quantity":2,"unitPrice":10.50,"currency":"EUR"}'
```

Copy the returned `id` value and replace `UUID` in the following commands:

```bash
curl http://localhost:8080/api/v1/orders/UUID
curl -X POST http://localhost:8080/api/v1/orders/UUID/confirm
curl -X POST http://localhost:8080/api/v1/orders/UUID/cancel
curl http://localhost:8080/actuator/health
```

Attempting to cancel an already confirmed order returns **HTTP 409 Conflict**. This is enforced by a domain invariant, not by validation logic in the REST controller.


## Known Limitations

- **In-memory H2 database:** Data is not persisted across application restarts.
- **`ddl-auto: create-drop`:** Intended for demonstration purposes only, not for production.
- **Excluded components:** Security, Swagger UI, Kafka, and Saga orchestration are covered by separate projects.
- **Concurrency handling:** Concurrent state transitions would require `@Version` or another locking mechanism, along with appropriate conflict handling.
- **Idempotency:** No idempotency key mechanism is implemented for order creation.

The project includes automated tests. However, the Maven build was not executed in the preparation environment because Maven was not installed.
