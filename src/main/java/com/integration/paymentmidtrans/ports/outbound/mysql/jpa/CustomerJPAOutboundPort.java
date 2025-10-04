package com.integration.paymentmidtrans.ports.outbound.mysql.jpa;

import com.integration.paymentmidtrans.shared.dto.coreapis.Customer;

public interface CustomerJPAOutboundPort {

    Boolean checkCustomerAndHasRole(String email, String customer);

    Customer getCustomerById(Long id);
}
