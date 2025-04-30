package com.integration.paymentmidtrans.adapter.outbound.mysql.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bank_accounts")
public class BankAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "bank_account_number", nullable = false, length = 30)
    private String bankAccountNumber;

    @Column(name = "bank_name", nullable = false, length = 50)
    private String bankName;

    @Column(name = "bank_account_holder", nullable = false, length = 100)
    private String bankAccountHolder;
}