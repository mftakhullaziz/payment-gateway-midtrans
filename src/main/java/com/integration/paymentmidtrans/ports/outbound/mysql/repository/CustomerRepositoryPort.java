package com.integration.paymentmidtrans.ports.outbound.mysql.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.integration.paymentmidtrans.adapter.outbound.mysql.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepositoryPort extends JpaRepository<CustomerEntity, Integer> {
    boolean existsByEmailAndRoleAndStatus(String email, String role, String status);

    Optional<CustomerEntity> findFirstById(Long id);
}
