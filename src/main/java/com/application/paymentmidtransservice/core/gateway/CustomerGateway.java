package com.application.paymentmidtransservice.core.gateway;

public interface CustomerGateway {

    Boolean checkCustomerAndHasRole(String email, String customer);
}
