package com.app.midtrans.infra.adapter.outbound.persistence.banks;

import com.app.midtrans.domain.bank.Bank;
import com.app.midtrans.domain.bank.BankPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BankPersistenceAdapter implements BankPersistencePort {

    private final BankJpaRepository bankJpaRepository;

    @Override
    public Bank getBankByName(String bankName) {
        return bankJpaRepository.findByName(bankName)
            .map(this::toDomain)
            .orElse(null);
    }

    private Bank toDomain(BankEntity entity) {
        return Bank.builder()
            .id(entity.getId())
            .name(entity.getName())
            .build();
    }

}
