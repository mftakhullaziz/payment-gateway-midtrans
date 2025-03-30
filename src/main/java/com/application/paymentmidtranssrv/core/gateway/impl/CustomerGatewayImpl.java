package com.application.paymentmidtranssrv.core.gateway.impl;

import com.application.paymentmidtranssrv.app.annotation.Gateway;
import com.application.paymentmidtranssrv.core.gateway.CustomerGateway;
import com.application.paymentmidtranssrv.infra.mysql.repository.CustomerRepo;
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
