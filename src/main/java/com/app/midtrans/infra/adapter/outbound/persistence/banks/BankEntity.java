package com.app.midtrans.infra.adapter.outbound.persistence.banks;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "banks")
public class BankEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    private String code; // BCA, BNI, MANDIRI

    @Column(name = "name", nullable = false)
    private String name; // Bank Central Asia

    @Column(name = "active", nullable = false)
    private boolean active;
}

