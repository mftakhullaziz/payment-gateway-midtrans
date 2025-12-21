package com.integration.adapter.ports.outbound.mysql.jpa;

import com.app.midtrans.shared.dto.coreapis.Customer;

public interface CustomerJPAOutboundPort {

    Boolean checkCustomerAndHasRole(String email, String customer);

    Customer getCustomerById(Long id);
}
