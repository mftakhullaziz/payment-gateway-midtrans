# Project Structure Documentation

> Comprehensive guide to the App Midtrans project structure following Hexagonal Architecture principles.

## 📋 Table of Contents

- [Overview](#-overview)
- [Complete Directory Tree](#-complete-directory-tree)
- [Layer Architecture](#-layer-architecture)
- [Domain Layer Details](#-domain-layer-details)
- [Application Layer Details](#-application-layer-details)
- [Infrastructure Layer Details](#-infrastructure-layer-details)
- [Shared Layer Details](#-shared-layer-details)
- [Design Patterns](#-design-patterns)
- [Dependency Flow](#-dependency-flow)
- [Naming Conventions](#-naming-conventions)

---

## 🎯 Overview

This project implements **Hexagonal Architecture (Ports & Adapters Pattern)**, also known as Clean Architecture. The structure ensures:

- ✅ **Business logic independence** from frameworks and external systems
- ✅ **High testability** through dependency inversion
- ✅ **Flexibility** to swap implementations without affecting core logic
- ✅ **Clear separation of concerns** across layers
- ✅ **Maintainability** through organized, predictable structure

### Architectural Principles

1. **Dependency Rule**: Dependencies point inward (Infrastructure → Application → Domain)
2. **Domain Isolation**: Core business logic has zero external dependencies
3. **Port-Adapter Pattern**: Domain defines interfaces (ports), infrastructure implements them (adapters)
4. **Single Responsibility**: Each component has one clear purpose

---

## 📂 Complete Directory Tree

```
app-midtrans/
│
├── 📄 AppMidtransApplication.java          # Spring Boot entry point
│
├── 📱 application/                          # USE CASE ORCHESTRATION LAYER
│   ├── UseCaseExecutor.java                # Generic use case executor
│   └── usecase/
│       ├── VaNotifyUseCase.java            # Handle payment notifications
│       └── VaPaymentUseCase.java           # Create VA payment requests
│
├── 🧠 domain/                               # CORE BUSINESS LOGIC LAYER
│   │
│   ├── bank/
│   │   ├── Bank.java                       # Bank entity (BCA, BNI, etc.)
│   │   ├── BankPersistencePort.java        # Port: Bank data operations
│   │   └── BankService.java                # Service: Bank business logic
│   │
│   ├── bankAccount/
│   │   ├── BankAccount.java                # Virtual account entity
│   │   ├── BankAccountPersistencePort.java # Port: VA data operations
│   │   └── BankAccountService.java         # Service: VA business logic
│   │
│   ├── callback/
│   │   ├── Callback.java                   # Payment callback entity
│   │   ├── CallbackPersistencePort.java    # Port: Callback storage
│   │   └── CallbackService.java            # Service: Callback processing
│   │
│   ├── customer/
│   │   ├── Customer.java                   # Customer entity
│   │   ├── CustomerPersistencePort.java    # Port: Customer data operations
│   │   └── CustomerService.java            # Service: Customer validation
│   │
│   ├── email/
│   │   ├── Email.java                      # Email entity/value object
│   │   ├── EmailGatewayPort.java           # Port: Email sending interface
│   │   └── EmailService.java               # Service: Email composition
│   │
│   └── payment/
│       ├── Payment.java                    # Payment entity
│       ├── PaymentGatewayPort.java         # Port: Midtrans integration
│       ├── PaymentPersistencePort.java     # Port: Payment storage
│       └── PaymentService.java             # Service: Payment orchestration
│
├── 🔌 infra/                                # INFRASTRUCTURE LAYER
│   │
│   ├── adapter/
│   │   │
│   │   ├── inbound/                        # INCOMING REQUESTS
│   │   │   │
│   │   │   ├── controller/
│   │   │   │   └── VaTransferController.java   # REST API endpoints
│   │   │   │
│   │   │   ├── messaging/
│   │   │   │   └── VirtualAccountMessaging.java # Message queue consumer
│   │   │   │
│   │   │   ├── request/
│   │   │   │   ├── VaTransferNotifyRequest.java # Callback DTO
│   │   │   │   └── VaTransferRequest.java       # Payment creation DTO
│   │   │   │
│   │   │   └── response/
│   │   │       └── VaTransferResponse.java      # API response DTO
│   │   │
│   │   └── outbound/                       # OUTGOING REQUESTS
│   │       │
│   │       ├── client/                     # External API clients
│   │       │   │
│   │       │   ├── mailtrap/
│   │       │   │   └── EmailGatewayAdapter.java     # Email service adapter
│   │       │   │
│   │       │   └── midtrans/
│   │       │       ├── MidtransGatewayAdapter.java  # Midtrans API client
│   │       │       ├── MidtransVARequest.java       # Midtrans request DTO
│   │       │       └── MidtransVAResponse.java      # Midtrans response DTO
│   │       │
│   │       └── persistence/                # Database adapters
│   │           │
│   │           ├── bankAccount/
│   │           │   ├── BankAccountAdapter.java              # Implements BankAccountPersistencePort
│   │           │   ├── BankAccountEntity.java               # JPA entity
│   │           │   └── BankAccountPersistenceJpaRepository.java # Spring Data JPA repo
│   │           │
│   │           ├── banks/
│   │           │   ├── BankEntity.java                      # JPA entity
│   │           │   ├── BankJpaRepository.java               # Spring Data JPA repo
│   │           │   └── BankPersistenceAdapter.java          # Implements BankPersistencePort
│   │           │
│   │           ├── callback/
│   │           │   ├── CallbackEntity.java                  # JPA entity
│   │           │   ├── CallbackJpaRepository.java           # Spring Data JPA repo
│   │           │   └── CallbackPersistenceAdapter.java      # Implements CallbackPersistencePort
│   │           │
│   │           ├── common/
│   │           │   ├── BaseRepositoryAdapter.java           # Base adapter with common logic
│   │           │   └── EntityMap.java                       # Entity-Domain mapper interface
│   │           │
│   │           ├── customer/
│   │           │   ├── CustomerEntity.java                  # JPA entity
│   │           │   ├── CustomerJpaRepository.java           # Spring Data JPA repo
│   │           │   └── CustomerPersistenceAdapter.java      # Implements CustomerPersistencePort
│   │           │
│   │           └── payment/
│   │               ├── PaymentEntity.java                   # JPA entity
│   │               ├── PaymentJpaRepository.java            # Spring Data JPA repo
│   │               └── PaymentPersistenceAdapter.java       # Implements PaymentPersistencePort
│   │
│   └── config/                             # Spring configurations
│       ├── CorsConfig.java                 # CORS policy setup
│       ├── OpenAPIConfig.java              # Swagger/OpenAPI config
│       ├── PersistenceConfig.java          # JPA/Database config
│       ├── RestClientConfig.java           # HTTP client beans
│       ├── ServerUrlResolver.java          # Dynamic URL resolution
│       └── WebConfig.java                  # Web MVC config
│
└── 🛠️ shared/                              # CROSS-CUTTING CONCERNS
    │
    ├── annotation/
    │   ├── Gateway.java                    # Custom annotation for gateway classes
    │   └── Usecase.java                    # Custom annotation for use cases
    │
    ├── aop/
    │   ├── LoggerAop.java                  # Logging aspect (method entry/exit)
    │   └── TransactionalAop.java           # Transaction management aspect
    │
    ├── enums/
    │   ├── BankType.java                   # Enum: BCA, BNI, MANDIRI, etc.
    │   ├── PaymentTypes.java               # Enum: VA, CREDIT_CARD, etc.
    │   └── VaChannel.java                  # Enum: BANK_TRANSFER channels
    │
    ├── exception/
    │   ├── BusinessException.java          # Base business exception
    │   ├── ExternalApiException.java       # External API call failures
    │   ├── InactiveCustomerException.java  # Customer validation error
    │   ├── InvalidCustomerRoleException.java
    │   ├── InvalidCustomerStateException.java
    │   └── NotifyEmailException.java       # Email sending failures
    │
    ├── handler/
    │   └── GlobalHandler.java              # Global exception handler (@ControllerAdvice)
    │
    ├── payload/
    │   └── Response.java                   # Standardized API response wrapper
    │
    ├── resources/
    │   ├── EmailResource.java              # Email template loader
    │   └── PaymentResource.java            # Payment-related resources
    │
    ├── restclient/
    │   └── RestClientInvoker.java          # HTTP client wrapper utility
    │
    └── utils/
        ├── Base64Utils.java                # Base64 encoding/decoding
        ├── JsonUtils.java                  # JSON serialization helpers
        ├── TimeUtils.java                  # Date/time formatting
        └── ValidatorUtils.java             # Input validation utilities
```

---

## 🏗️ Layer Architecture

### Visual Layer Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                         PRESENTATION                         │
│  ┌────────────────────┐         ┌──────────────────────┐   │
│  │  REST Controllers  │         │  Messaging Handlers  │   │
│  └────────────────────┘         └──────────────────────┘   │
└────────────────┬────────────────────────────────────────────┘
                 │
                 │ DTO Request/Response
                 ▼
┌─────────────────────────────────────────────────────────────┐
│                        APPLICATION                           │
│  ┌──────────────────────────────────────────────────────┐  │
│  │              Use Case Orchestration                   │  │
│  │  • VaPaymentUseCase   • VaNotifyUseCase              │  │
│  └──────────────────────────────────────────────────────┘  │
└────────────────┬────────────────────────────────────────────┘
                 │
                 │ Domain Objects
                 ▼
┌─────────────────────────────────────────────────────────────┐
│                          DOMAIN                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐   │
│  │  Payment │  │ Customer │  │   Bank   │  │  Email   │   │
│  │ Service  │  │ Service  │  │ Service  │  │ Service  │   │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘   │
│                                                              │
│  ┌──────────────────────────────────────────────────────┐  │
│  │              PORTS (Interfaces)                       │  │
│  │  • PersistencePorts  • GatewayPorts                  │  │
│  └──────────────────────────────────────────────────────┘  │
└────────────────┬────────────────────────────────────────────┘
                 │
                 │ Port Implementation
                 ▼
┌─────────────────────────────────────────────────────────────┐
│                      INFRASTRUCTURE                          │
│  ┌────────────────┐  ┌────────────────┐  ┌──────────────┐  │
│  │   JPA/Database │  │  Midtrans API  │  │ Email SMTP   │  │
│  │    Adapters    │  │     Client     │  │   Client     │  │
│  └────────────────┘  └────────────────┘  └──────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

---

## 🧠 Domain Layer Details

The **Domain Layer** is the heart of the application containing all business logic and rules.

### Structure Pattern

Each domain aggregate follows this structure:

```
domain/{aggregate}/
├── {Aggregate}.java              # Core entity/value object
├── {Aggregate}Service.java       # Business operations
├── {Aggregate}PersistencePort.java  # Data access interface
└── {Aggregate}GatewayPort.java   # External service interface (if needed)
```

### Domain Components

#### 1. **Bank Domain** (`domain/bank/`)

**Purpose**: Manage bank master data and validation

| File | Type | Responsibility |
|------|------|----------------|
| `Bank.java` | Entity | Bank information (code, name, status) |
| `BankService.java` | Service | Validate bank availability, fetch bank details |
| `BankPersistencePort.java` | Port | Interface for bank data operations |

**Business Rules**:
- Bank must be active to process payments
- Bank codes must be standardized (BCA, BNI, MANDIRI, etc.)
- Each bank has specific VA number format rules

#### 2. **BankAccount Domain** (`domain/bankAccount/`)

**Purpose**: Virtual Account lifecycle management

| File | Type | Responsibility |
|------|------|----------------|
| `BankAccount.java` | Entity | VA details (number, customer, bank, status) |
| `BankAccountService.java` | Service | Create VA, validate VA, update status |
| `BankAccountPersistencePort.java` | Port | VA persistence operations |

**Business Rules**:
- VA number must be unique per bank
- VA can be ACTIVE, INACTIVE, or EXPIRED
- VA belongs to one customer and one bank

#### 3. **Callback Domain** (`domain/callback/`)

**Purpose**: Payment notification processing

| File | Type | Responsibility |
|------|------|----------------|
| `Callback.java` | Entity | Callback data from Midtrans |
| `CallbackService.java` | Service | Process notifications, validate signatures |
| `CallbackPersistencePort.java` | Port | Store callback history |

**Business Rules**:
- All callbacks must be logged for audit
- Duplicate callbacks should be idempotent
- Failed callback processing should be retried

#### 4. **Customer Domain** (`domain/customer/`)

**Purpose**: Customer validation and management

| File | Type | Responsibility |
|------|------|----------------|
| `Customer.java` | Entity | Customer information |
| `CustomerService.java` | Service | Validate customer eligibility for payment |
| `CustomerPersistencePort.java` | Port | Customer data operations |

**Business Rules**:
- Customer must be active to make payments
- Customer must have valid role permissions
- Customer state must allow transactions

#### 5. **Email Domain** (`domain/email/`)

**Purpose**: Email notification composition

| File | Type | Responsibility |
|------|------|----------------|
| `Email.java` | Value Object | Email content (to, subject, body) |
| `EmailService.java` | Service | Compose email templates with data |
| `EmailGatewayPort.java` | Port | Email sending interface |

**Email Types**:
- **Reminder Email**: Sent when payment is created
- **Success Email**: Sent when payment is confirmed
- **Failed Email**: Sent when payment fails/expires

#### 6. **Payment Domain** (`domain/payment/`)

**Purpose**: Core payment processing logic

| File | Type | Responsibility |
|------|------|----------------|
| `Payment.java` | Entity | Payment transaction details |
| `PaymentService.java` | Service | Payment orchestration, status transitions |
| `PaymentPersistencePort.java` | Port | Payment data operations |
| `PaymentGatewayPort.java` | Port | Midtrans API interface |

**Payment State Machine**:
```
PENDING → PROCESSING → SUCCESS
                     ↘ FAILED
                     ↘ EXPIRED
```

**Business Rules**:
- Payment amount must be positive
- Order ID must be unique
- Payment status transitions must follow state machine
- Expired payments cannot be processed

---

## 📱 Application Layer Details

The **Application Layer** orchestrates use cases by coordinating domain services and ports.

### Components

#### 1. **UseCaseExecutor.java**

**Purpose**: Generic executor for running use cases within transaction boundaries

```java
@Component
public class UseCaseExecutor {
    public <I, O> O execute(UseCase<I, O> useCase, I input) {
        // Execute use case with proper error handling
        // Manage transaction boundaries
        // Log execution metrics
    }
}
```

#### 2. **VaPaymentUseCase.java**

**Purpose**: Create Virtual Account payment request

**Flow**:
1. Validate customer eligibility (CustomerService)
2. Validate bank availability (BankService)
3. Create payment in Midtrans (PaymentGatewayPort)
4. Save payment record (PaymentPersistencePort)
5. Create/update virtual account (BankAccountService)
6. Send reminder email (EmailService + EmailGatewayPort)
7. Return VA details to client

**Input**: `VaTransferRequest` (customerId, bankCode, amount, orderId)

**Output**: `VaTransferResponse` (vaNumber, amount, expiry, paymentUrl)

#### 3. **VaNotifyUseCase.java**

**Purpose**: Process payment notification from Midtrans

**Flow**:
1. Validate callback signature
2. Store callback for audit (CallbackService)
3. Find payment by order ID (PaymentService)
4. Update payment status (PaymentService)
5. Update VA status (BankAccountService)
6. Send confirmation email (EmailService)
7. Acknowledge callback to Midtrans

**Input**: `VaTransferNotifyRequest` (Midtrans callback payload)

**Output**: Success/failure acknowledgment

---

## 🔌 Infrastructure Layer Details

The **Infrastructure Layer** implements technical concerns and adapters for external systems.

### Inbound Adapters

Handle incoming requests and convert them to domain operations.

#### Controllers (`infra/adapter/inbound/controller/`)

**VaTransferController.java**

```java
@RestController
@RequestMapping("/api/v1/va")
public class VaTransferController {

    @PostMapping("/transfer")
    public ResponseEntity<Response<VaTransferResponse>> createPayment(
        @RequestBody VaTransferRequest request) {
        // Convert DTO to use case input
        // Execute VaPaymentUseCase
        // Return standardized response
    }

    @PostMapping("/notify")
    public ResponseEntity<Void> handleCallback(
        @RequestBody VaTransferNotifyRequest request) {
        // Execute VaNotifyUseCase
        // Return 200 OK to Midtrans
    }
}
```

#### Messaging (`infra/adapter/inbound/messaging/`)

**VirtualAccountMessaging.java**

Handles asynchronous payment notifications from message queues (RabbitMQ, Kafka, etc.)

```java
@Component
public class VirtualAccountMessaging {

    @RabbitListener(queues = "payment.notifications")
    public void handlePaymentNotification(VaTransferNotifyRequest request) {
        // Process notification asynchronously
        // Execute VaNotifyUseCase
    }
}
```

### Outbound Adapters

Implement domain ports to communicate with external systems.

#### Persistence Adapters (`infra/adapter/outbound/persistence/`)

Each persistence package follows this pattern:

```
{domain}/
├── {Domain}Entity.java              # JPA entity with annotations
├── {Domain}JpaRepository.java       # Spring Data repository interface
└── {Domain}PersistenceAdapter.java  # Implements {Domain}PersistencePort
```

**Example: PaymentPersistenceAdapter**

```java
@Component
public class PaymentPersistenceAdapter
    extends BaseRepositoryAdapter<Payment, PaymentEntity>
    implements PaymentPersistencePort {

    private final PaymentJpaRepository repository;

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = toEntity(payment);
        PaymentEntity saved = repository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Payment> findByOrderId(String orderId) {
        return repository.findByOrderId(orderId)
            .map(this::toDomain);
    }
}
```

**Common Patterns**:
- `BaseRepositoryAdapter`: Shared mapping logic
- `EntityMap`: Interface for entity ↔ domain conversion
- Optimistic locking for concurrent updates
- Soft deletes for audit trail

#### Gateway Adapters (`infra/adapter/outbound/client/`)

**MidtransGatewayAdapter.java**

```java
@Gateway
public class MidtransGatewayAdapter implements PaymentGatewayPort {

    private final RestClientInvoker restClient;

    @Override
    public PaymentResponse createVirtualAccount(PaymentRequest request) {
        MidtransVARequest midtransRequest = convertToMidtransFormat(request);

        MidtransVAResponse response = restClient.post(
            midtransApiUrl,
            midtransRequest,
            MidtransVAResponse.class,
            createAuthHeaders()
        );

        return convertToDomainResponse(response);
    }
}
```

**EmailGatewayAdapter.java**

```java
@Gateway
public class EmailGatewayAdapter implements EmailGatewayPort {

    private final JavaMailSender mailSender;

    @Override
    public void send(Email email) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email.getTo());
        helper.setSubject(email.getSubject());
        helper.setText(email.getBody(), true);

        mailSender.send(message);
    }
}
```

### Configuration (`infra/config/`)

| File | Purpose |
|------|---------|
| `CorsConfig.java` | Cross-Origin Resource Sharing policies |
| `OpenAPIConfig.java` | Swagger/OpenAPI documentation setup |
| `PersistenceConfig.java` | JPA, datasource, transaction management |
| `RestClientConfig.java` | HTTP client beans (RestTemplate, WebClient) |
| `ServerUrlResolver.java` | Dynamic base URL resolution for callbacks |
| `WebConfig.java` | Web MVC interceptors, converters, formatters |

---

## 🛠️ Shared Layer Details

Cross-cutting concerns used across all layers.

### Annotations (`shared/annotation/`)

Custom annotations for semantic marking:

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Usecase {
    String value() default "";
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Gateway {
    String value() default "";
}
```

### AOP (`shared/aop/`)

**LoggerAop.java**: Automatic logging for annotated methods

```java
@Aspect
@Component
public class LoggerAop {

    @Around("@within(com.example.shared.annotation.Usecase)")
    public Object logUseCase(ProceedingJoinPoint joinPoint) {
        log.info("Executing use case: {}", joinPoint.getSignature());
        Object result = joinPoint.proceed();
        log.info("Use case completed: {}", joinPoint.getSignature());
        return result;
    }
}
```

**TransactionalAop.java**: Declarative transaction management

### Enums (`shared/enums/`)

| Enum | Values | Purpose |
|------|--------|---------|
| `BankType` | BCA, BNI, MANDIRI, PERMATA, etc. | Supported banks |
| `PaymentTypes` | BANK_TRANSFER, CREDIT_CARD, E_WALLET | Payment methods |
| `VaChannel` | BCA_VA, BNI_VA, MANDIRI_VA | VA-specific channels |

### Exception Hierarchy (`shared/exception/`)

```
BusinessException (Base)
├── InactiveCustomerException
├── InvalidCustomerRoleException
├── InvalidCustomerStateException
├── ExternalApiException
│   └── NotifyEmailException
└── ... (other business exceptions)
```

### Global Exception Handler (`shared/handler/`)

```java
@ControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Response<Void>> handleBusinessException(
        BusinessException ex) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Response.error(ex.getMessage()));
    }

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<Response<Void>> handleExternalApiException(
        ExternalApiException ex) {
        return ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(Response.error("External service error"));
    }
}
```

### Utilities (`shared/utils/`)

| Utility | Purpose | Key Methods |
|---------|---------|-------------|
| `Base64Utils` | Encoding/decoding | `encode()`, `decode()` |
| `JsonUtils` | JSON operations | `toJson()`, `fromJson()`, `prettyPrint()` |
| `TimeUtils` | Date/time handling | `now()`, `format()`, `parse()`, `isExpired()` |
| `ValidatorUtils` | Input validation | `isValidEmail()`, `isValidPhone()`, `sanitize()` |

---

## 🎨 Design Patterns

### 1. **Repository Pattern**

Domain defines ports, infrastructure implements repositories:

```java
// Domain Port
public interface PaymentPersistencePort {
    Payment save(Payment payment);
    Optional<Payment> findById(Long id);
}

// Infrastructure Adapter
@Component
public class PaymentPersistenceAdapter implements PaymentPersistencePort {
    // Implementation using JPA
}
```

### 2. **Gateway Pattern**

External API communication abstracted behind gateways:

```java
// Domain Port
public interface PaymentGatewayPort {
    PaymentResponse createVirtualAccount(PaymentRequest request);
}

// Infrastructure Adapter
@Gateway
public class MidtransGatewayAdapter implements PaymentGatewayPort {
    // Midtrans-specific implementation
}
```

### 3. **Service Layer Pattern**

Business logic encapsulated in domain services:

```java
@Service
public class PaymentService {
    public Payment processPayment(PaymentRequest request) {
        // Business rules and validation
    }
}
```

### 4. **Use Case Pattern**

Application-specific workflows:

```java
@Usecase
public class VaPaymentUseCase implements UseCase<VaTransferRequest, VaTransferResponse> {
    @Override
    public VaTransferResponse execute(VaTransferRequest input) {
        // Orchestrate domain services and ports
    }
}
```

### 5. **DTO Pattern**

Separate DTOs for API layer:

```
Request → Controller → UseCase (with Domain Objects) → Response
```

### 6. **Adapter Pattern**

Convert between different representations:

```java
// Entity to Domain
Payment toDomain(PaymentEntity entity)

// Domain to Entity
PaymentEntity toEntity(Payment domain)

// DTO to Domain
Payment fromRequest(VaTransferRequest request)
```

---

## 🔄 Dependency Flow

### Allowed Dependencies

```
┌─────────────┐
│   Shared    │ ← Can be used by all layers
└─────────────┘

┌─────────────┐
│    Infra    │ → Application → Domain
└─────────────┘

Infrastructure CAN depend on: Application, Domain, Shared
Application CAN depend on: Domain, Shared
Domain CAN depend on: Shared (enums, exceptions only)
Shared CANNOT depend on: Any other layer
```

### Forbidden Dependencies

❌ Domain → Infrastructure (violates dependency rule)
❌ Domain → Application (domain should be reusable)
❌ Shared → Any other layer (shared is foundation)

### Example: Creating a Payment

```
VaTransferController (infra/inbound)
         ↓
VaPaymentUseCase (application)
         ↓
CustomerService (domain) → CustomerPersistencePort (domain)
         ↓                          ↓
BankService (domain)     CustomerPersistenceAdapter (infra/outbound)
         ↓                          ↓
PaymentService (domain)    CustomerJpaRepository (infra/outbound)
         ↓
PaymentGatewayPort (domain)
         ↓
MidtransGatewayAdapter (infra/outbound)
```

---

## 📝 Naming Conventions

### Package Naming

| Layer | Package Pattern | Example |
|-------|-----------------|---------|
| Domain | `domain.{aggregate}` | `domain.payment` |
| Application | `application.usecase` | `application.usecase` |
| Infrastructure | `infra.adapter.{direction}.{type}` | `infra.adapter.outbound.client` |
| Shared | `shared.{category}` | `shared.utils` |

### Class Naming

| Type | Suffix | Example |
|------|--------|---------|
| Entity | No suffix | `Payment`, `Customer` |
| Service | `Service` | `PaymentService` |
| Port | `Port` | `PaymentPersistencePort` |
| Adapter | `Adapter` | `PaymentPersistenceAdapter` |
| Controller | `Controller` | `VaTransferController` |
| Use Case | `UseCase` | `VaPaymentUseCase` |
| DTO | `Request`/`Response` | `VaTransferRequest` |
| Exception | `Exception` | `BusinessException` |
| Enum | No suffix | `BankType` |
| Utility | `Utils` | `JsonUtils` |

### Method Naming

| Operation | Pattern | Example |
|-----------|---------|---------|
| Create | `create*` | `createPayment()` |
| Retrieve | `get*`/`find*` | `getById()`, `findByOrderId()` |
| Update | `update*` | `updateStatus()` |
| Delete | `delete*`/`remove*` | `deleteById()` |
| Validate | `validate*`/`is*` | `validateCustomer()`, `isActive()` |
| Convert | `to*`/`from*` | `toEntity()`, `fromRequest()` |

---

## 🔍 How to Navigate the Project

### Finding Business Logic
1. Start in `domain/{aggregate}/` - Look for the relevant domain service
2. Check the service class for business rules and validation
3. Look at the entity for data structure and invariants

**Example**: To understand payment processing logic:
```
domain/payment/PaymentService.java  ← Start here for business logic
domain/payment/Payment.java         ← Entity structure and rules
```

### Finding API Endpoints
1. Go to `infra/adapter/inbound/controller/`
2. Find the relevant controller
3. Trace the use case being called

**Example**: Finding VA transfer endpoint:
```
infra/adapter/inbound/controller/VaTransferController.java
  → @PostMapping("/transfer")
    → calls VaPaymentUseCase
```

### Understanding Use Case Flow
1. Start in `application/usecase/{UseCaseName}.java`
2. Follow the orchestration of domain services
3. Check which ports are being used
4. Trace to infrastructure adapters

**Example**: VA Payment flow:
```
application/usecase/VaPaymentUseCase.java
  ↓ calls CustomerService.validate()
  ↓ calls BankService.findByCode()
  ↓ calls PaymentGatewayPort.create()
  ↓ calls PaymentPersistencePort.save()
  ↓ calls EmailGatewayPort.send()
```

### Finding Database Queries
1. Look in `infra/adapter/outbound/persistence/{domain}/`
2. Check `{Domain}JpaRepository.java` for custom queries
3. Check `{Domain}PersistenceAdapter.java` for implementation

**Example**: Finding payment queries:
```
infra/adapter/outbound/persistence/payment/
  → PaymentJpaRepository.java          ← Custom JPA queries
  → PaymentPersistenceAdapter.java     ← Implementation logic
```

### Understanding External API Calls
1. Go to `infra/adapter/outbound/client/{service}/`
2. Find the gateway adapter
3. Check request/response DTOs

**Example**: Midtrans integration:
```
infra/adapter/outbound/client/midtrans/
  → MidtransGatewayAdapter.java    ← API calls implementation
  → MidtransVARequest.java         ← Request structure
  → MidtransVAResponse.java        ← Response structure
```

### Adding New Features

#### 1. Adding a New Payment Method (e.g., Credit Card)

**Step 1**: Create domain model
```
domain/creditcard/
  ├── CreditCard.java
  ├── CreditCardService.java
  └── CreditCardPersistencePort.java
```

**Step 2**: Create use case
```
application/usecase/
  └── CreditCardPaymentUseCase.java
```

**Step 3**: Implement infrastructure
```
infra/adapter/outbound/persistence/creditcard/
  ├── CreditCardEntity.java
  ├── CreditCardJpaRepository.java
  └── CreditCardPersistenceAdapter.java
```

**Step 4**: Create controller
```
infra/adapter/inbound/controller/
  └── CreditCardController.java
```

#### 2. Adding a New External Service Integration

**Step 1**: Define gateway port in domain
```java
// domain/notification/NotificationGatewayPort.java
public interface NotificationGatewayPort {
    void sendSms(String phone, String message);
}
```

**Step 2**: Create adapter in infrastructure
```java
// infra/adapter/outbound/client/twilio/TwilioGatewayAdapter.java
@Gateway
public class TwilioGatewayAdapter implements NotificationGatewayPort {
    @Override
    public void sendSms(String phone, String message) {
        // Twilio implementation
    }
}
```

**Step 3**: Use in domain service or use case
```java
// domain/notification/NotificationService.java
public class NotificationService {
    private final NotificationGatewayPort notificationGateway;
    
    public void notifyCustomer(Customer customer, String message) {
        notificationGateway.sendSms(customer.getPhone(), message);
    }
}
```

---

## 🧪 Testing Strategy

### Unit Testing Structure

```
src/test/java/
├── domain/
│   ├── payment/
│   │   ├── PaymentServiceTest.java        # Test business logic
│   │   └── PaymentTest.java               # Test entity invariants
│   └── customer/
│       └── CustomerServiceTest.java
│
├── application/
│   └── usecase/
│       ├── VaPaymentUseCaseTest.java      # Mock domain services
│       └── VaNotifyUseCaseTest.java
│
└── infra/
    ├── adapter/
    │   ├── inbound/
    │   │   └── VaTransferControllerTest.java  # Integration test
    │   └── outbound/
    │       ├── persistence/
    │       │   └── PaymentPersistenceAdapterTest.java  # Repository test
    │       └── client/
    │           └── MidtransGatewayAdapterTest.java     # Mock external API
    └── config/
        └── ConfigurationTest.java
```

### Testing Guidelines by Layer

**Domain Layer Tests**:
- Pure unit tests with no mocking
- Test business rules and validation
- Test entity state transitions
- No Spring context needed

```java
@Test
void shouldRejectNegativePaymentAmount() {
    assertThrows(BusinessException.class, () -> {
        Payment.create(-1000, "ORDER-001");
    });
}
```

**Application Layer Tests**:
- Mock domain services and ports
- Test orchestration logic
- Verify correct service calls

```java
@ExtendWith(MockitoExtension.class)
class VaPaymentUseCaseTest {
    @Mock private PaymentService paymentService;
    @Mock private PaymentGatewayPort paymentGateway;
    
    @Test
    void shouldCreateVaPayment() {
        // Test use case orchestration
    }
}
```

**Infrastructure Layer Tests**:
- Integration tests with real dependencies
- Use `@SpringBootTest` or `@DataJpaTest`
- Test database operations
- Test API integrations (with mocked servers)

```java
@DataJpaTest
class PaymentPersistenceAdapterTest {
    @Autowired
    private PaymentJpaRepository repository;
    
    @Test
    void shouldSavePayment() {
        // Test repository operations
    }
}
```

---

## 📊 Common Scenarios

### Scenario 1: Customer Makes a Payment

**Request Flow**:
```
1. POST /api/v1/va/transfer
   ↓
2. VaTransferController.createPayment()
   ↓
3. VaPaymentUseCase.execute()
   ├─→ CustomerService.validate(customerId)
   ├─→ BankService.findByCode(bankCode)
   ├─→ PaymentGatewayPort.createVirtualAccount()
   ├─→ PaymentPersistencePort.save(payment)
   ├─→ BankAccountService.create(vaDetails)
   └─→ EmailService.sendReminder(customer, payment)
   ↓
4. Return VaTransferResponse (VA number, amount, expiry)
```

**Files Involved**:
- `VaTransferController.java` - Entry point
- `VaPaymentUseCase.java` - Orchestration
- `CustomerService.java`, `BankService.java`, `PaymentService.java` - Business logic
- `MidtransGatewayAdapter.java` - External API
- `PaymentPersistenceAdapter.java` - Database
- `EmailGatewayAdapter.java` - Email sending

### Scenario 2: Midtrans Sends Payment Notification

**Callback Flow**:
```
1. POST /api/v1/va/notify (from Midtrans)
   ↓
2. VaTransferController.handleCallback()
   ↓
3. VaNotifyUseCase.execute()
   ├─→ CallbackService.save(callbackData)
   ├─→ PaymentService.findByOrderId(orderId)
   ├─→ PaymentService.updateStatus(SETTLEMENT)
   ├─→ BankAccountService.updateStatus(USED)
   └─→ EmailService.sendSuccess(customer, payment)
   ↓
4. Return 200 OK to Midtrans
```

**Files Involved**:
- `VaTransferController.java` - Callback endpoint
- `VaNotifyUseCase.java` - Notification processing
- `CallbackService.java` - Audit logging
- `PaymentService.java` - Status update
- `EmailGatewayAdapter.java` - Success notification

### Scenario 3: Querying Payment History

**Query Flow**:
```
1. GET /api/v1/payments?customerId={id}
   ↓
2. PaymentController.getPaymentHistory()
   ↓
3. PaymentQueryUseCase.execute()
   └─→ PaymentPersistencePort.findByCustomerId(customerId)
   ↓
4. Return List<PaymentResponse>
```

---

## 🚨 Common Pitfalls to Avoid

### ❌ Don't: Put Business Logic in Controllers

```java
// BAD - Business logic in controller
@PostMapping("/transfer")
public Response createPayment(@RequestBody VaTransferRequest request) {
    if (request.getAmount() <= 0) {  // ❌ Business validation here
        throw new BusinessException("Invalid amount");
    }
    // More business logic...
}
```

```java
// GOOD - Delegate to use case
@PostMapping("/transfer")
public Response createPayment(@RequestBody VaTransferRequest request) {
    VaTransferResponse response = useCaseExecutor.execute(
        vaPaymentUseCase, 
        request
    );
    return Response.success(response);
}
```

### ❌ Don't: Make Domain Depend on Infrastructure

```java
// BAD - Domain importing Spring JPA
import javax.persistence.Entity;

@Entity  // ❌ Domain should not know about JPA
public class Payment {
    // ...
}
```

```java
// GOOD - Keep domain pure
public class Payment {  // ✅ Plain Java object
    private Long id;
    private BigDecimal amount;
    // Business logic only
}

// Infrastructure has the JPA entity
@Entity
public class PaymentEntity {  // ✅ Infrastructure concern
    // JPA annotations here
}
```

### ❌ Don't: Skip Port Interfaces

```java
// BAD - Direct dependency on adapter
public class PaymentService {
    @Autowired
    private MidtransGatewayAdapter midtransAdapter;  // ❌ Tight coupling
}
```

```java
// GOOD - Depend on port interface
public class PaymentService {
    private final PaymentGatewayPort paymentGateway;  // ✅ Abstraction
    
    public PaymentService(PaymentGatewayPort paymentGateway) {
        this.paymentGateway = paymentGateway;
    }
}
```

### ❌ Don't: Return Entities from Controllers

```java
// BAD - Exposing domain entities
@GetMapping("/payments/{id}")
public Payment getPayment(@PathVariable Long id) {  // ❌ Domain entity exposed
    return paymentService.findById(id);
}
```

```java
// GOOD - Use DTOs
@GetMapping("/payments/{id}")
public PaymentResponse getPayment(@PathVariable Long id) {  // ✅ DTO
    Payment payment = paymentService.findById(id);
    return PaymentResponse.from(payment);
}
```

---

## 🔧 Maintenance Guidelines

### Adding a New Domain Aggregate

1. Create domain structure:
   ```
   domain/{aggregate}/
   ├── {Aggregate}.java
   ├── {Aggregate}Service.java
   ├── {Aggregate}PersistencePort.java
   └── {Aggregate}GatewayPort.java (if needed)
   ```

2. Implement persistence:
   ```
   infra/adapter/outbound/persistence/{aggregate}/
   ├── {Aggregate}Entity.java
   ├── {Aggregate}JpaRepository.java
   └── {Aggregate}PersistenceAdapter.java
   ```

3. Create use cases if needed:
   ```
   application/usecase/
   └── {Aggregate}UseCase.java
   ```

### Modifying Existing Features

1. **Identify the layer** where change is needed
2. **Check dependencies** - will this affect other components?
3. **Update tests** for modified components
4. **Update documentation** if API changes

### Code Review Checklist

- [ ] Business logic is in domain layer
- [ ] Controllers only handle HTTP concerns
- [ ] Use cases orchestrate, don't contain business rules
- [ ] Ports are used for external dependencies
- [ ] DTOs are used for API boundaries
- [ ] Tests cover new functionality
- [ ] No circular dependencies
- [ ] Exceptions are properly handled
- [ ] Logging is appropriate
- [ ] Code follows naming conventions

---

## 📚 Additional Resources

### Architecture Patterns
- [Hexagonal Architecture by Alistair Cockburn](https://alistair.cockburn.us/hexagonal-architecture/)
- [Clean Architecture by Robert C. Martin](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Domain-Driven Design by Eric Evans](https://www.domainlanguage.com/ddd/)

### Spring Boot Best Practices
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA Best Practices](https://spring.io/guides/gs/accessing-data-jpa/)

### Related Documentation
- [README.md](README.md) - Project overview and setup
- [API_DOCUMENTATION.md](API_DOCUMENTATION.md) - API endpoint details
- [CONTRIBUTING.md](CONTRIBUTING.md) - Contribution guidelines

---

## 🎓 Learning Path for New Developers

### Week 1: Understanding the Structure
1. Read this document thoroughly
2. Study the domain layer (entities and services)
3. Trace one complete flow from controller to database
4. Run the application and test endpoints

### Week 2: Making Small Changes
1. Add a new field to an existing entity
2. Create a new API endpoint
3. Write unit tests for your changes
4. Submit a pull request

### Week 3: Feature Development
1. Implement a new use case
2. Add a new domain aggregate
3. Integrate with an external service
4. Write comprehensive tests

### Week 4: Architecture Deep Dive
1. Refactor existing code to improve design
2. Review and critique other PRs
3. Propose architectural improvements
4. Document your learnings

---

## 📞 Getting Help

### Questions About Architecture
- Review this document first
- Check existing code examples
- Ask in team architecture channel
- Schedule architecture review session

### Questions About Implementation
- Check related use cases for patterns
- Review test cases for examples
- Consult domain experts for business rules
- Pair program with senior developers

---

**Last Updated**: December 2025  
**Maintained By**: Miftakhul Aziz  
**Version**: 1.0