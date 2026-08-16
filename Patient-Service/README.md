# Patient-Service

Core patient management service providing full CRUD operations for the Patient Management system.

## Overview

This service manages patient records and coordinates with other services:
- Calls **billing-service** via **gRPC** to create a billing account on patient registration
- Publishes a **Kafka** `PATIENT_CREATED` event to the `patient` topic when a new patient is added

## Endpoints

| Method | Path | Description |
|---|---|---|
| `GET` | `/patients` | List all patients |
| `POST` | `/patients` | Create a new patient |
| `PUT` | `/patients/{id}` | Update an existing patient |
| `DELETE` | `/patients/{id}` | Delete a patient |

### Create Patient Request

```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "address": "123 Main Street, City",
  "dateOfBirth": "1990-01-15",
  "registeredDate": "2026-08-01"
}
```

## Configuration

| Property | Default | Description |
|---|---|---|
| `server.port` | `4000` | HTTP server port |
| `billing.service.address` | `localhost` | Billing service gRPC host |
| `billing.service.grpc.port` | `9001` | Billing service gRPC port |
| `spring.kafka.bootstrap-servers` | `localhost:9092` | Kafka broker address |

## Running

```bash
./mvnw spring-boot:run
```

Swagger UI: `http://localhost:4000/swagger-ui/index.html`
