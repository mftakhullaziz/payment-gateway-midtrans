package com.app.midtrans.infrastructure.adapter.outbound.persistence.customer;

import com.app.midtrans.domain.customer.CustomerPort;
import com.app.midtrans.shared.dto.coreapis.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerAdapter implements CustomerPort {


    @Override
    public Boolean checkCustomerAndHasRole(String email, String customer) {
        return null;
    }

    @Override
    public Customer getCustomerById(Long id) {
        return null;
    }
}
