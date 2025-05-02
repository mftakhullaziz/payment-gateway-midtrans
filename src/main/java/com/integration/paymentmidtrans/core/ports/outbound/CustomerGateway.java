package com.integration.paymentmidtrans.core.ports.outbound;

import com.integration.paymentmidtrans.core.dto.Customer;

public interface CustomerGateway {

    Boolean checkCustomerAndHasRole(String email, String customer);

    Customer getCustomerById(Long id);
}
