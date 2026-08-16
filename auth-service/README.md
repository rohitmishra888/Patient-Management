# auth-service

Authentication service providing JWT-based login and token validation for the Patient Management system.

## Overview

Exposes REST endpoints for user authentication. Tokens generated here are validated by the API Gateway before forwarding requests to downstream services.

## Endpoints

| Method | Path | Description |
|---|---|---|
| `POST` | `/login` | Authenticate with email + password, returns JWT |
| `GET` | `/validate` | Validate a Bearer JWT token |

## Configuration

| Property | Description |
|---|---|
| `server.port` | `4005` |
| `jwt.secret` | Base64-encoded HMAC-SHA secret key |
| `spring.datasource.*` | PostgreSQL / H2 datasource config |

JWT tokens are valid for **10 hours**.

## Running

```bash
./mvnw spring-boot:run
```

Swagger UI: `http://localhost:4005/swagger-ui/index.html`
