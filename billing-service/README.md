# billing-service

Billing service provides gRPC-based billing account management for the Patient Management system.

## Overview

This service exposes a gRPC server (port 9001) that creates billing accounts for newly registered patients.
It is called synchronously by the patient-service via CreateBillingAccount RPC when a patient is created.

## gRPC Contract

`proto
service BillingService {
  rpc CreateBillingAccount (BillingRequest) returns (BillingResponse);
}
`

## Running

`ash
./mvnw spring-boot:run
`

gRPC server listens on port 9001 (configurable via grpc.server.port).
