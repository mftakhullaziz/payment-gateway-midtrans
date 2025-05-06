package com.integration.paymentmidtrans.ports.outbound.mysql.repository;

import com.integration.paymentmidtrans.adapter.outbound.mysql.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepositoryPort extends JpaRepository<CustomerEntity, Long> {
    boolean existsByEmailAndRoleAndStatus(String email, String role, String status);
}
