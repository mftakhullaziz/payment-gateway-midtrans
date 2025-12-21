package com.app.midtrans.domain.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private Long id;
    private String email;
    private String name;
    private String phone;
    private String role; // CUSTOMER, MERCHANT, VENDOR
    private String status;

    public boolean isActive() {
        return "ACTIVE".equals(status);
    }

    public boolean isCustomerRole() {
        return "CUSTOMER".equals(role);
    }
}
