package com.integration.paymentmidtrans.adapter.outbound.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "merchant_id")
    private String merchantId;

    @Column(name = "gross_amount")
    private Double grossAmount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "transaction_time")
    private Timestamp transactionTime;

    @Column(name = "transaction_status")
    private String transactionStatus;

    @Column(name = "expiry_time")
    private Timestamp expiryTime;

    @Column(name = "fraud_status")
    private String fraudStatus;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_va_numbers")
    private String paymentVaNumbers;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "total_tax")
    private Double totalTax;

    @Column(name = "total_discount")
    private Double totalDiscount;

    @Column(name = "payment_channel")
    private String paymentChannel;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}