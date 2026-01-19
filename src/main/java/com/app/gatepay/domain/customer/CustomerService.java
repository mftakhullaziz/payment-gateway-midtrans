package com.app.gatepay.domain.customer;

import com.app.gatepay.shared.exception.InactiveCustomerException;
import com.app.gatepay.shared.exception.InvalidCustomerRoleException;
import com.app.gatepay.shared.exception.InvalidCustomerStateException;
import org.springframework.stereotype.Component;

@Component
public class CustomerService {

    public void activate(Customer customer) {
        if (!"PENDING".equals(customer.getStatus())) {
            throw new InvalidCustomerStateException("Customer status must be PENDING to activate");
        }
        customer.setStatus("ACTIVE");
    }

    public void deactivate(Customer customer) {
        customer.setStatus("INACTIVE");
    }

    public void ensureEligibleForPayment(Customer customer) {
        if (customer == null) {
            throw new InvalidCustomerStateException("Customer must be present to make payment.");
        }

        if (customer.getId() == null) {
            throw new InvalidCustomerStateException("Customer id must be present to make payment.");
        }

        if (!customer.isCustomerRole()) {
            throw new InvalidCustomerRoleException("Customer role must be CUSTOMER to make payment.");
        }

        if (!customer.isActive()) {
            throw new InactiveCustomerException("Customer must be active to make payment.");
        }
    }

    public void customerIdMustBePresent(Long customerId) {
        if (customerId == null || customerId.equals(0L)) {
            throw new IllegalArgumentException("Customer id must not be null or empty");
        }
    }

}
