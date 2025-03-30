package com.application.paymentmidtranssrv.infra.mysql.repository;

import com.application.paymentmidtranssrv.infra.mysql.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity, Long> {
    boolean existsByEmailAndRoleAndStatus(String email, String role, String status);
}
