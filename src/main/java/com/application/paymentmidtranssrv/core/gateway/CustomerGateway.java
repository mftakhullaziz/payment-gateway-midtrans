package com.application.paymentmidtranssrv.core.gateway;

public interface CustomerGateway {

    Boolean checkCustomerAndHasRole(String email, String customer);
}
