# 🏥 Patient Management System

A microservices-based patient management system built with **Java Spring Boot**, demonstrating modern backend architecture patterns including REST APIs, gRPC communication, Apache Kafka event streaming, and JWT authentication.

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                       API Gateway (:4004)                    │
│               Spring Cloud Gateway (WebFlux)                 │
└──────────────────────┬──────────────────┬───────────────────┘
                       │                  │
           ┌───────────▼──────┐  ┌────────▼──────────────┐
           │  auth-service    │  │   patient-service      │
           │  (:4005)         │  │   (:4000)              │
           │  JWT Auth        │  │   Core CRUD            │
           └──────────────────┘  └────────┬───────────────┘
                                          │
                          ┌───────────────┼─────────────────┐
                          │               │                  │
                  ┌───────▼──────┐  ┌────▼─────┐  ┌────────▼────────┐
                  │ billing-     │  │  Kafka   │  │ analytics-      │
                  │ service      │  │ (topic:  │  │ service         │
                  │ gRPC (:9001) │  │ patient) │  │ Kafka Consumer  │
                  └──────────────┘  └──────────┘  └─────────────────┘
```

---

## 📦 Services

| Service | Port | Description |
|---|---|---|
| `api-gateway` | 4004 | Single entry point — routes all incoming requests |
| `auth-service` | 4005 | JWT-based authentication (login + token validation) |
| `patient-service` | 4000 | Patient CRUD, triggers billing and analytics events |
| `billing-service` | gRPC :9001 | Creates billing accounts for new patients via gRPC |
| `analytics-service` | — | Consumes patient events from Kafka for analytics |

---

## 🔌 Communication Patterns

| Pattern | Between | Details |
|---|---|---|
| **REST (HTTP)** | Client → Gateway → Services | Spring Cloud Gateway routing |
| **gRPC** (sync) | patient-service → billing-service | `CreateBillingAccount` RPC on patient creation |
| **Kafka** (async) | patient-service → analytics-service | `PatientEvent` protobuf on `patient` topic |

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- Docker & Docker Compose

### Running with Docker Compose

```bash
# Clone the repository
git clone https://github.com/rohitmishra888/Patient-Management.git
cd Patient-Management

# Copy and configure environment
cp .env.example .env

# Start all services
docker compose up --build
```

### Running Individually

```bash
# Start each service from its directory
cd Patient-Service && ./mvnw spring-boot:run
cd auth-service    && ./mvnw spring-boot:run
cd billing-service && ./mvnw spring-boot:run
cd analytics-service && ./mvnw spring-boot:run
cd api-gateway     && ./mvnw spring-boot:run
```

---

## 📋 API Endpoints

### Auth Service (`/auth`)

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/auth/login` | Login with email + password, returns JWT |
| `GET` | `/auth/validate` | Validate a Bearer token |

### Patient Service (`/api/patients`)

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/patients` | Get all patients |
| `POST` | `/api/patients` | Create a new patient |
| `PUT` | `/api/patients/{id}` | Update an existing patient |
| `DELETE` | `/api/patients/{id}` | Delete a patient |

### Swagger / OpenAPI

- Patient Service: `http://localhost:4000/swagger-ui/index.html`
- Auth Service: `http://localhost:4005/swagger-ui/index.html`

---

## 🛠️ Technology Stack

| Technology | Usage |
|---|---|
| Java 17 | Primary language |
| Spring Boot 3.x / 4.x | Application framework |
| Spring Cloud Gateway | API Gateway (reactive/WebFlux) |
| Spring Data JPA | Database ORM |
| PostgreSQL | Production database |
| H2 (in-memory) | Development/testing database |
| gRPC + Protobuf | Synchronous service-to-service calls |
| Apache Kafka | Async event streaming |
| Spring Security + JWT (JJWT) | Authentication & authorization |
| springdoc-openapi | Swagger/OpenAPI documentation |
| Docker | Containerization |

---

## 📁 Project Structure

```
Patient-Management/
├── api-gateway/         # Spring Cloud Gateway
├── auth-service/        # JWT authentication
├── Patient-Service/     # Core patient management
├── billing-service/     # Billing account management (gRPC server)
├── analytics-service/   # Event analytics (Kafka consumer)
├── api-requests/        # HTTP request test files
├── grpc-requests/       # gRPC test request files
├── docker-compose.yml   # Local development stack
└── .env.example         # Environment variable template
```

---

## 🔐 Environment Variables

See [`.env.example`](.env.example) for all configurable variables.

---

## 📝 License

This project is for educational purposes.


## ?? Test Request Files

HTTP request test files are in the `api-requests/` directory.
gRPC test request files are in the `grpc-requests/` directory.
