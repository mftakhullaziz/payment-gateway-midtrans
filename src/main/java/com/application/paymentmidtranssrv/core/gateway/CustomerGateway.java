package com.application.paymentmidtranssrv.core.gateway;

import com.application.paymentmidtranssrv.domain.model.Customer;

public interface CustomerGateway {

    Boolean checkCustomerAndHasRole(String email, String customer);

    Customer getCustomerById(Long id);
}
