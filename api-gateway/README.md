# api-gateway

API Gateway service built with Spring Cloud Gateway (WebFlux/reactive) for the Patient Management system.

## Overview

All client requests enter through this gateway. It handles routing to downstream services.

## Routes

| External Path | Downstream Service | Notes |
|---|---|---|
| `/auth/**` | `auth-service:4005` | StripPrefix=1 (removes /auth prefix) |
| `/api/patients/**` | `patient-service:4000` | StripPrefix=1 (removes /api prefix) |
| `/api-docs/patients` | `patient-service:4000/v3/api-docs` | RewritePath for Swagger docs |

## Running

```bash
./mvnw spring-boot:run
```

Gateway listens on port `4004`.
