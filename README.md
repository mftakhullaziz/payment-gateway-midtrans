# Payment Gateway OpenAPI (Gatepay)

A provider-agnostic payment gateway service built with **Hexagonal Architecture**.

Gatepay exposes a stable **OpenAPI** for creating payments and handling webhooks, while letting you choose the underlying payment provider (Midtrans, DOKU, Xendit, Faspay, OY Indonesia, etc.) by registering each provider’s credentials.

> Status: **Phase 1 implemented** — `/api/v2/payments` is available with `methodType=VA` routed to Midtrans. More providers/methods are on the roadmap.

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![OpenAPI](https://img.shields.io/badge/OpenAPI-3.x-blue.svg)](https://spec.openapis.org/oas/latest.html)

---

## Table of Contents

- [Why Gatepay](#why-gatepay)
- [Key Features](#key-features)
- [Architecture Overview](#architecture-overview)
- [Supported Providers](#supported-providers)
- [API (OpenAPI)](#api-openapi)
  - [V2 (provider-agnostic)](#v2-provider-agnostic)
  - [V1 (legacy compatibility)](#v1-legacy-compatibility)
- [Configuration](#configuration)
- [Running Locally](#running-locally)
- [Email Notifications](#email-notifications)
- [Testing](#testing)
- [Roadmap](#roadmap)
- [License](#license)

---

## Why Gatepay

When you integrate payments directly to a provider, switching providers later is painful. Gatepay introduces a stable internal contract (canonical payment model) so client applications:

- don’t need to know provider-specific request/response shapes,
- can switch providers by changing configuration/credentials,
- can centralize webhook verification, status mapping, and persistence.

---

## Key Features

- **Provider-agnostic Payment API (OpenAPI)** for creating and tracking payments
- **Provider routing** (request-level override now, config default supported)
- **Payment history tracking** with persistence + callback audit trail
- **Automated email notifications** (reminder & payment status)
- **Hexagonal Architecture** (clean separation of domain, use cases, adapters)

---

## Architecture Overview

This application implements **Hexagonal Architecture (Ports & Adapters)**.

```
┌─────────────────────────────────────────────────────────┐
│                    INBOUND ADAPTERS                      │
│         (REST Controllers, Webhook Handlers)             │
└────────────────────┬────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────┐
│                  APPLICATION LAYER                       │
│              (Use Cases & Orchestration)                 │
└────────────────────┬────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────┐
│                    DOMAIN LAYER                          │
│         (Entities, Rules, Ports/Interfaces)              │
└────────────────────┬────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────┐
│                   OUTBOUND ADAPTERS                      │
│     (DB, Provider Clients, Email, Cache, etc.)           │
└─────────────────────────────────────────────────────────┘
```

- **Domain**: canonical payment model + provider-agnostic ports.
- **Application**: use cases to create payments, handle callbacks, send emails.
- **Infrastructure**: provider clients (Midtrans/DOKU/Xendit/…), persistence, config.

Detailed structure: see [`PROJECT_STRUCTURE.md`](PROJECT_STRUCTURE.md)

---

## Supported Providers

Planned provider adapters:

- Midtrans (**implemented for VA in Phase 1**)
- DOKU
- Xendit
- Faspay
- OY Indonesia

---

## API (OpenAPI)

### Swagger / OpenAPI

Once the app is running:

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

### V2 (provider-agnostic)

#### Create payment

`POST /api/v2/payments`

**Phase 1 contract**

- `methodType=VA` ✅ implemented via Midtrans adapter
- `methodType=EWALLET` 🚧 accepted by API, provider adapter implementation pending

Request (VA example):

```json
{
  "provider": "MIDTRANS",
  "customerId": 1,
  "orderId": "ORDER-2026-001",
  "totalAmount": 100000,
  "methodType": "VA",
  "channel": "bca"
}
```

Notes:
- `provider` is optional. If omitted, Gatepay uses `gatepay.payment.default-provider`.

Response (example):

```json
{
  "data": {
    "customerId": 1,
    "orderId": "ORDER-2026-001",
    "provider": "MIDTRANS",
    "transactionId": "...",
    "status": "pending",
    "paymentType": "bank_transfer",
    "bank": "bca",
    "virtualAccountNumber": "1234567890",
    "transactionTime": "2026-01-19 10:00:00",
    "expiredTime": "2026-01-20 10:00:00"
  }
}
```

#### Webhooks

`POST /api/v2/webhooks/{provider}`

Phase 1:
- `POST /api/v2/webhooks/MIDTRANS` ✅ (payload shape follows Midtrans VA notification DTO)


### V1 (legacy compatibility)

Current endpoints are Midtrans-focused and remain available during migration:

- `POST /api/v1.0/va/transfer` — create VA payment
- `POST /api/v1.0/va/notify` — Midtrans callback

---

## Configuration

Gatepay uses `src/main/resources/application.yml` for configuration.

### Provider credentials (Phase 1: env-based)

Default provider:

- `PAYMENT_DEFAULT_PROVIDER` (default: `MIDTRANS`)

Midtrans credentials:

- `MERCHANT_ID`
- `CLIENT_KEY`
- `SERVER_KEY`
- `MIDTRANS_HOSTNAME` (optional; default: `https://api.sandbox.midtrans.com`)

> Legacy config under `application.external-service.payment-gateway.midtrans` is still present for backward compatibility.

---

## Running Locally

Prerequisites:

- Java 17+
- Maven 3.6+
- MySQL (based on default driver) and Redis (optional)

Build and run:

```bash
./mvnw clean test
./mvnw spring-boot:run
```

---

## Email Notifications

Gatepay sends automated emails at key points in the payment lifecycle.

### Payment Reminder Email

![Remainder Email](docs/remainder-payment-email.png)

### Payment Status Email

![Payment Success](docs/payment-successfully-email.png)

Templates:

- `src/main/resources/templates/payment-reminder-notification.html`
- `src/main/resources/templates/payment-status-notification.html`

---

## Testing

```bash
./mvnw test
```

---

## Roadmap

- [x] Add `/api/v2/payments` unified endpoint (Phase 1: VA)
- [ ] Add webhook v2 endpoint(s) + signature verification framework
- [ ] Add canonical `PaymentIntent` and normalized status model
- [ ] Add credential registry (DB) with encryption-at-rest
- [ ] Add provider routing rules (merchant default / method-based)
- [ ] Add adapters: Xendit, DOKU, Faspay, OY Indonesia
- [ ] Contract tests for OpenAPI + regression tests for v1

---

## License

This project is licensed under the MIT License. See [`LICENSE`](./LICENSE).
