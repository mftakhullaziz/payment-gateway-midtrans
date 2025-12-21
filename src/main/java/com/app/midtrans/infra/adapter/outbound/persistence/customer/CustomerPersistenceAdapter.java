package com.app.midtrans.infra.adapter.outbound.persistence.customer;

import com.app.midtrans.domain.customer.Customer;
import com.app.midtrans.domain.customer.CustomerPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerPersistenceAdapter implements CustomerPersistencePort {

    private final CustomerJpaRepository customerJpaRepository;

    @Override
    public Boolean checkCustomerAndHasRole(String email, String customer) {
        return null;
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerJpaRepository.findById(id)
            .map(this::toDomain)
            .orElse(null);
    }

    private Customer toDomain(CustomerEntity entity) {
        return Customer.builder()
            .id(entity.getId())
            .name(entity.getName())
            .email(entity.getEmail())
            .phone(entity.getPhone())
            .build();
    }

}
