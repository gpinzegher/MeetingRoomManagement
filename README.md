# Meeting Room Management

Mini REST API for meeting room registration, reservations, availability checks, and mock responses.

## Requirements

- Java 25

## Run the application

With Maven Wrapper:

```bash
./mvnw spring-boot:run
```

With local Maven:

```bash
mvn spring-boot:run
```

## Run in MOCK mode

Edit **app.mock.enabled** (true/false) in application.properties

## Useful URLs

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- H2 Console: `http://localhost:8080/h2-console` (real mode only)

## Run tests

```bash
./mvnw test
```
