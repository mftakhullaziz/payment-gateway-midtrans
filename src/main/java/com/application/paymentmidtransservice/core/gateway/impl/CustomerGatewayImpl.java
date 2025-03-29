package com.application.paymentmidtransservice.core.gateway.impl;

import com.application.paymentmidtransservice.app.annotation.Gateway;
import com.application.paymentmidtransservice.core.gateway.CustomerGateway;
import com.application.paymentmidtransservice.infra.mysql.repository.CustomerRepo;
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
}
