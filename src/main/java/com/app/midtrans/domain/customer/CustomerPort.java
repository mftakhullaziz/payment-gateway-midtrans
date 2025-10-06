package com.app.midtrans.domain.customer;

import com.app.midtrans.shared.dto.coreapis.Customer;

public interface CustomerPort {
    Boolean checkCustomerAndHasRole(String email, String customer);
    Customer getCustomerById(Long id);
}
