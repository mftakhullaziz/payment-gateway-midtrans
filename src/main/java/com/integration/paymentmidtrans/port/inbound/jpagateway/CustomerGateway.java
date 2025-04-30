package com.integration.paymentmidtrans.port.inbound.jpagateway;

import com.integration.paymentmidtrans.domain.coreapis.dto.Customer;

public interface CustomerGateway {

    Boolean checkCustomerAndHasRole(String email, String customer);

    Customer getCustomerById(Long id);
}
