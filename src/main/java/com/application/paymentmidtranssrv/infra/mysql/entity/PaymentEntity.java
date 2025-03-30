package com.application.paymentmidtranssrv.infra.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer paymentId;
    private Integer userId;
    private String orderId;
    private String transactionId;
    private String merchantId;
    private Double grossAmount;
    private String currency;
    private Timestamp transactionTime;
    private String transactionStatus;
    private Timestamp expiryTime;
    private String fraudStatus;
    private String paymentType;
    private String paymentMethod;
    private String paymentVaNumbers;
    private Double totalPrice;
    private Double totalTax;
    private Double totalDiscount;
    private Timestamp createdAt;
    private String createdBy;
    private Timestamp updatedAt;
    private String updatedBy;
}

// {
//  "status_code": 201,
//  "status_message": "Success, Bank Transfer transaction is created",
//  "transaction_id": "2e803a63-1cd1-4a99-bf93-c77104bf8cde",
//  "order_id": "sample_order_id12",
//  "merchant_id": "G122340993",
//  "gross_amount": "100.00",
//  "currency": "IDR",
//  "payment_type": "bank_transfer",
//  "transaction_time": "2023-12-25 21:29:53",
//  "transaction_status": "pending",
//  "expiry_time": "2023-12-26 21:29:53",
//  "fraud_status": "accept",
//  "va_numbers": [
//    {
//      "bank": "bca",
//      "va_number": "40993236630"
//    }
//  ]
//}