package com.integration.adapter.outbound.mysql.jpa;

import com.app.midtrans.shared.annotation.Gateway;
import com.integration.adapter.ports.outbound.mysql.jpa.CustomerJPAOutboundPort;
import com.app.midtrans.shared.dto.coreapis.Customer;
import com.integration.adapter.ports.outbound.mysql.repository.CustomerRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Gateway
@RequiredArgsConstructor
public class CustomerJpaAdapter implements CustomerJPAOutboundPort {

    private final CustomerRepositoryPort customerRepo;

    @Override
    public Boolean checkCustomerAndHasRole(String email, String customer) {
        if ("".equalsIgnoreCase(email) ||
            email == null) {
            return false;
        }

        if (customer == null ||
            customer.isBlank() ||
            !"CUSTOMER".equalsIgnoreCase(customer)) {
            return false;
        }

        return customerRepo.existsByEmailAndRoleAndStatus(email, customer, "ACTIVE");
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        return customerRepo.findFirstById(customerId)
            .map(v -> Customer.builder()
                .id(v.getId())
                .name(v.getName())
                .email(v.getEmail())
                .phone(v.getPhone())
                .build())
            .orElse(null);
    }
}
