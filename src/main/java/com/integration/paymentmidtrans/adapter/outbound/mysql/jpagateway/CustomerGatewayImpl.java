package com.integration.paymentmidtrans.adapter.outbound.mysql.jpagateway;

import com.integration.paymentmidtrans.shared.annotation.Gateway;
import com.integration.paymentmidtrans.core.ports.outbound.CustomerGateway;
import com.integration.paymentmidtrans.core.dto.Customer;
import com.integration.paymentmidtrans.core.ports.outbound.repository.CustomerRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Gateway
@RequiredArgsConstructor
public class CustomerGatewayImpl implements CustomerGateway {

    private final CustomerRepo customerRepo;

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
        return customerRepo.findById(customerId)
            .map(v -> Customer.builder()
                .id(v.getId())
                .email(v.getEmail())
                .phone(v.getPhone())
                .name(v.getName())
                .build())
            .orElse(null);
    }
}
