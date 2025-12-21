package com.app.midtrans.domain.customer;

public interface CustomerPersistencePort {
    Boolean checkCustomerAndHasRole(String email, String customer);
    Customer getCustomerById(Long id);
}
