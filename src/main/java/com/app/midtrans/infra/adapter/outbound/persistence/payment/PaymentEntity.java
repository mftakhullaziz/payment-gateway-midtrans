package com.app.midtrans.infra.adapter.outbound.persistence.payment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // === Ownership ===
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "order_id", nullable = false, unique = true)
    private String orderId;

    // === Provider Reference ===
    @Column(name = "provider", nullable = false)
    private String provider; // MIDTRANS, XENDIT, etc

    @Column(name = "provider_transaction_id")
    private String providerTransactionId;

    // === Amount ===
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "currency", length = 3)
    private String currency;

    // === Status ===
    @Column(name = "status")
    private String status; // PENDING, SETTLEMENT, FAILED

    @Column(name = "payment_time")
    private Timestamp paymentTime;

    // === Payment Classification ===
    @Column(name = "channel")
    private String channel; // VA, EWALLET, CC

    @Column(name = "method")
    private String method; // BCA_VA, MANDIRI_VA, PERMATA_VA

    // === VA / BILL ===
    @Column(name = "bank_code")
    private String bankCode; // bca, bni, bri

    @Column(name = "reference_number")
    private String referenceNumber; // VA number / bill key

    // === Audit ===
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
