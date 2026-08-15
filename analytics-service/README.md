# analytics-service

Analytics service consumes patient events from Apache Kafka and processes them for analytics purposes.

## Overview

This service listens on the patient Kafka topic (group: nalytics-service) and deserializes
Protobuf-encoded PatientEvent messages published by the patient-service.

## Event Schema

`proto
message PatientEvent {
  string patientId = 1;
  string name      = 2;
  string email     = 3;
  string event_type = 4;  // e.g. PATIENT_CREATED
}
`

## Configuration

| Property | Default | Description |
|---|---|---|
| spring.kafka.bootstrap-servers | localhost:9092 | Kafka broker address |
| spring.kafka.consumer.group-id | nalytics-service | Consumer group ID |
| server.port | 4002 | HTTP server port |

## Running

`ash
./mvnw spring-boot:run
`

Requires a Kafka broker running on localhost:9092 (or configured via SPRING_KAFKA_BOOTSTRAP_SERVERS).
