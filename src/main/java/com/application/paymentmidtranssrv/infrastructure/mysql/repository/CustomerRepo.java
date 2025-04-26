package com.application.paymentmidtranssrv.infrastructure.mysql.repository;

import com.application.paymentmidtranssrv.infrastructure.mysql.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity, Long> {
    boolean existsByEmailAndRoleAndStatus(String email, String role, String status);
}
