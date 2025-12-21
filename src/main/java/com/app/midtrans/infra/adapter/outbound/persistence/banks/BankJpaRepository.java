package com.app.midtrans.infra.adapter.outbound.persistence.banks;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankJpaRepository extends JpaRepository<BankEntity, Long> {
    Optional<BankEntity> findByName(String name);
}
